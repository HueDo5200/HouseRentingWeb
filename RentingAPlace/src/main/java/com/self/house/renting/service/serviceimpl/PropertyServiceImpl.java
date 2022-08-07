package com.self.house.renting.service.serviceimpl;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.exception.ResourceExistedException;
import com.self.house.renting.model.dto.request.SearchCriteriaRequest;
import com.self.house.renting.model.dto.request.ManagedPropertyRequest;
import com.self.house.renting.exception.ResourceNotFoundException;
import com.self.house.renting.repository.PropertyImageRepository;
import com.self.house.renting.repository.PropertyRepository;
import com.self.house.renting.repository.custom.PropertyCustomRepository;
import com.self.house.renting.repository.specification.PropertySpecification;
import com.self.house.renting.service.PropertyService;
import com.self.house.renting.util.DtoUtil;
import com.self.house.renting.model.dto.response.ManagedPropertyResponse;
import com.self.house.renting.model.dto.response.PropertyDetailedResponse;
import com.self.house.renting.model.dto.response.PropertyImagesResponse;
import com.self.house.renting.model.dto.response.PropertyResponse;
import com.self.house.renting.model.entity.Property;
import com.self.house.renting.model.entity.PropertyImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Value("${imagePath}")
    private String imagePath;

    private PropertyRepository propertyRepository;
    private PropertyImageRepository imageRepository;
    private PropertyCustomRepository propertyCustomRepository;


    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyImageRepository imageRepository, PropertyCustomRepository propertyCustomRepository) {
        this.propertyRepository = propertyRepository;
        this.imageRepository = imageRepository;
        this.propertyCustomRepository = propertyCustomRepository;

    }

    @Override
    public ManagedPropertyResponse save(ManagedPropertyRequest property) {
        handleExistedPropertyByNameException(property.getName());
        Property p = property.fromDtoToProperty();
        Property result = propertyRepository.save(p);
        return result.toManagedProperty();
    }

    @Override
    public ManagedPropertyResponse update(int propId, ManagedPropertyRequest property) {
        property.setId(propId);
        Property p = property.fromDtoToProperty();
        Property result = propertyRepository.save(p);
        return result.toManagedProperty();
    }


    @Override
    public void delete(int id) {
        Property property = handlePropertyNotFoundException(id);
        List<PropertyImage> images = property.getPropertyImages();
        images.forEach(image -> {
            String fileName = image.getName();
            Path path = Paths.get(imagePath + "\\" + fileName);
            try {
                if(Files.exists(path)) {
                    Files.delete(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        propertyRepository.deleteById(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Property handlePropertyNotFoundException(int id) {
      return propertyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));
    }

    @Override
    public void handleExistedPropertyByNameException(String name) {
       if(propertyRepository.existsByName(name)) {
           throw new ResourceExistedException(Constants.RESOURCE_ALREADY_EXISTED);
       }
    }

    /**
     *
     * @param fileName not null or empty
     * @param id > 0
     */
    @Override
    public void uploadImage(String fileName, int id) {
        propertyCustomRepository.uploadImage(fileName, id);
    }

    /**
     *
     * @param propId > 0
     * @param customerId > 0
     * @throws ResourceNotFoundException if the property is not found with propId
     * @return  return property with detailed information
     */
    @Override
    public PropertyDetailedResponse getPropertyDto(int propId, int customerId) {
        Property p = handlePropertyNotFoundException(propId);
        return p.toPropertyDetailedResponse(customerId);
    }





    /**
     *
     * @param keyword not null
     * @param pageNo >= 0
     * @param pageSize > 0
     * @param sortBy must not be null or empty
     * @return sorted searching properties satisfying searching keywords
     */
    @Override
    public Map<String, Object> searchProperty(String keyword, int pageNo, int pageSize, String sortBy) {
//        String[] arr = keyword.split(" ");
//        StringBuilder builder = new StringBuilder();
//        for (String element : arr) {
//            builder.append(element);
//        }
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Property> pagedResults = propertyRepository.findAll(PropertySpecification.hasAttributeLike(keyword), paging);
        return populatePagingPropertyMap(pagedResults);

    }

    @Override
    public Map<String, Object> searchPropertyByCriteria(SearchCriteriaRequest criteria, int pageNo, int pageSize, String sortBy) {
       return propertyCustomRepository.searchPropertyByCriteria(criteria, pageNo, pageSize, sortBy);
    }

    /**
     *
     * @param pageNo >= 0
     * @param pageSize > 0
     * @param sortBy not null or empty
     * @return all properties in the database
     */
    public Map<String, Object> getAllPropertyDto(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Property> pagedResults = propertyRepository.findAll(paging);
        return populatePagingPropertyMap(pagedResults);
    }

    /**
     *
     * @param userId > 0
     * @param pageNo >= 0
     * @param pageSize > 0
     * @param sortBy not null or empty
     * @return all properties not reserved by a customer
     */
    public Map<String, Object> getAvailablePropertiesDto(int userId, int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
           Page<Property> pagedResults = propertyRepository.findAll(PropertySpecification.hasUnReservedProperty(userId), paging);
           if(pagedResults.isEmpty()) {
               return getAllPropertyResponse(pageNo, pageSize, sortBy);
           }
        return populatePagingPropertyMap(pagedResults);
    }

    public Map<String, Object> getAllPropertyResponse(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Property> pagedResults = propertyRepository.findAll(paging);
        if(pagedResults.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.DATA_KEY, pagedResults.getContent());
        return populateMapPropertyWithPaginationInfo(map, pagedResults);
    }

    /**
     *
     * @param userId > 0
     * @param pageNo >= 0
     * @param pageSize > 0
     * @param sortBy must not be null or empty
     * @return reserved properties of a customer
     */
    public Map<String, Object> getCustomerPropertiesDto(int userId, int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
       Page<Property> pagedResults = propertyRepository.findAll(PropertySpecification.hasReservedPropertyOfCustomer(userId), paging);
        return populatePagingPropertyMap(pagedResults);
    }


    /**
     *
     * @param ownerId > 0
     * @param pageNo >= 0
     * @param pageSize > 0
     * @param sortBy must not be null or empty
     * @return all properties of an owner
     */
    @Transactional
    @Override
    public Map<String, Object> findAllPropertyOfOwner(int ownerId, int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Property> pagedResults = propertyRepository.findAll(PropertySpecification.hasPropertyOfOwner(ownerId), paging);
        return populatePagingManagedPropertyMap(pagedResults);

    }

    /**
     *
     * @param propId must be larger than 0
     * @return all images of a property
     */
    @Override
    public PropertyImagesResponse findImagesOfProperty(int propId) {
      Property p = propertyRepository.findById(propId).orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));
      return p.getImagesListFromProperty();
    }

    /**
     *
     * @param imageId > 0
     */
    @Override
    public void deleteImageOfProperty(int imageId) {
       PropertyImage image = imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));
        String fileName = image.getName();
        Path path = Paths.get(imagePath + "\\" + fileName);
        try {
            if(Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.imageRepository.deleteById(imageId);
    }

    /**
     *
     * @param properties must not be null or empty
     * @return property responses for managing by owner
     */
    public List<ManagedPropertyResponse> toManagedPropertyResponseList(List<Property> properties) {
        return properties.stream().map(Property::toManagedProperty).collect(Collectors.toList());
    }



    /**
     *
     * @param myFile must not be null
     * @param path must not be null or empty
     * @param fileName - must not be null or empty
     */
    public void transferFile(MultipartFile myFile, String path, String fileName) {
        try {
            Files.write(Paths.get(path + "\\" + fileName), myFile.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param pagedResults must not be null
     * @return
     *          if page has no content
     *                  return empty map
     *          else
     *                  return map of objects with information of property response list, current page, total pages, number of items
     */
    public Map<String, Object> populatePagingPropertyMap(Page<Property> pagedResults) {
        if(pagedResults.isEmpty()) {
           return new HashMap<>();
        }
        List<PropertyResponse> list = DtoUtil.toPropertyResponses(pagedResults.getContent());
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        return populateMapPropertyWithPaginationInfo(map, pagedResults);
    }

    /**
     *
     * @param pagedResults must not be null
     * @return if page has no content
     *              return empty map
     *         else
     *              initialize map with information of property list, current page, total pages, number of items
     *              return populated map
     */
    public Map<String, Object> populatePagingManagedPropertyMap(Page<Property> pagedResults) {
        if(pagedResults.isEmpty()) {
         return new HashMap<>();
        }
        List<ManagedPropertyResponse> list = toManagedPropertyResponseList(pagedResults.getContent());
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.DATA_KEY, list);
        return populateMapPropertyWithPaginationInfo(map, pagedResults);
    }

    public Map<String, Object> populateMapPropertyWithPaginationInfo(Map<String, Object> map, Page<Property> pagedResults) {
        map.put(Constants.CURRENT_PAGE_KEY, pagedResults.getNumber());
        map.put(Constants.TOTAL_PAGE_KEY, pagedResults.getTotalPages());
        map.put(Constants.ITEMS_NUMBER_KEY, pagedResults.getTotalElements());
        return map;
    }







}

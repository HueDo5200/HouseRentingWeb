package com.self.house.renting.service;

import com.self.house.renting.model.dto.request.SearchCriteriaRequest;
import com.self.house.renting.model.dto.request.ManagedPropertyRequest;
import com.self.house.renting.model.dto.response.ManagedPropertyResponse;
import com.self.house.renting.model.dto.response.PropertyDetailedResponse;
import com.self.house.renting.model.dto.response.PropertyImagesResponse;
import com.self.house.renting.model.entity.Property;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PropertyService {
    Map<String, Object> searchPropertyByCriteria(SearchCriteriaRequest criteria, int pageNo, int pageSize, String sortBy);
    Map<String, Object> getAllPropertyDto(int pageNo, int pageSize, String sortBy);
    Map<String, Object> getAvailablePropertiesDto(int userId, int pageNo, int pageSize, String sortBy);
    Map<String, Object> getCustomerPropertiesDto(int userId, int pageSize, int pageNo, String sortBy);
    ManagedPropertyResponse save(ManagedPropertyRequest property);
    ManagedPropertyResponse update(int propId, ManagedPropertyRequest property);
    void delete(int id);
    Property handlePropertyNotFoundException(int id);
    void handleExistedPropertyByNameException(String name);
    Map<String, Object> searchProperty(String keyword, int pageNo, int pageSize, String sortBy);
    void uploadImage(String fileName, int id);
    void transferFile(MultipartFile myFile, String path, String fileName);
    PropertyDetailedResponse getPropertyDto(int propId, int customerId);
    Map<String, Object> findAllPropertyOfOwner(int ownerId, int pageNo, int pageSize, String sortBy);
    PropertyImagesResponse findImagesOfProperty(int propId);
    void deleteImageOfProperty(int imageId);
}

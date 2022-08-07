package com.self.house.renting.controller;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.model.dto.request.ManagedPropertyRequest;
import com.self.house.renting.model.dto.request.SearchCriteriaRequest;
import com.self.house.renting.model.dto.response.ManagedPropertyResponse;
import com.self.house.renting.util.ResponseUtil;
import com.self.house.renting.service.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Validated
@RestController@RequestMapping(Constants.PROPERTY_API)
public class PropertyController {
 Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Value("${imagePath}")
    private String imagePath;

    private PropertyService propertyService;


    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping(value = "/public/properties/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("fileName") @NotBlank String fileName) {
        byte[] image = new byte[0];
        try {
            image = Files.readAllBytes(Paths.get(imagePath + "\\" + fileName));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping(value = "/public/properties", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) @Min(0) int pageNo, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) @Min(1) int pageSize, @RequestParam(defaultValue = Constants.DEFAULT_PROPERTY_SORT_BY) @NotBlank String sortBy) {
        Map<String, Object> properties = propertyService.getAllPropertyDto(pageNo, pageSize, sortBy);
        if(properties.isEmpty()) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
       return ResponseUtil.customizeMapResponse(properties, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }

    @PreAuthorize("hasAuthority('customer') and #id == authentication.principal.id")
    @GetMapping(value = "/properties/available/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findUnreservedProperties(@PathVariable @Min(1) int id, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) @Min(0) int pageNo, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) @Min(1) int pageSize, @RequestParam(defaultValue = Constants.DEFAULT_PROPERTY_SORT_BY) @NotBlank String sortBy) {
        Map<String, Object> properties = propertyService.getAvailablePropertiesDto(id, pageNo, pageSize, sortBy);
        if(properties.isEmpty()) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeMapResponse(properties, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }

    @PreAuthorize("hasAuthority('customer') and authentication.principal.id == #id")
    @GetMapping(value = "/properties/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findReservedPropertiesOfCustomer(@PathVariable @Min(1) int id, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) @Min(0) int pageNo, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) @Min(1) int pageSize, @RequestParam(defaultValue = Constants.DEFAULT_PROPERTY_SORT_BY) @NotBlank String sortBy) {
        Map<String, Object> properties = propertyService.getCustomerPropertiesDto(id, pageNo, pageSize, sortBy);
        if(properties.isEmpty()) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeMapResponse(properties, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }

    @GetMapping(value = "/public/properties/{id}/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findPropertyById(@PathVariable @Min(1) int id, @PathVariable int customerId) {
        return ResponseUtil.customizeResponse(propertyService.getPropertyDto(id, customerId), HttpStatus.OK, Constants.GET_RESOURCE_SUCCESS);
    }


    @GetMapping(value = "/public/properties/criteria", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchPropertyByCriteria(@RequestParam(defaultValue = Constants.DEFAULT_LOCATION_VALUE) @NotNull String place, @RequestParam(defaultValue = Constants.DEFAULT_TYPE_VALUE) @NotNull String type, @RequestParam("amenities") @Nullable List<Integer> amenities, @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss") @NotNull LocalDateTime checkInDate, @RequestParam("checkoutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss") @NotNull LocalDateTime checkoutDate, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) @Min(0)
            int pageNo, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) @Min(1) int pageSize, @RequestParam(defaultValue = Constants.DEFAULT_PROPERTY_SORT_BY) @NotBlank String sortBy) {
 //           Map<String, Object> properties = propertyService.searchPropertyByCriteria(SearchCriteriaRequest.builder().amenity(amenity).type(type).location(place).checkoutDate(checkoutDate).checkInDate(checkInDate).build(), pageNo, pageSize, sortBy);
        Map<String, Object> properties = propertyService.searchPropertyByCriteria(SearchCriteriaRequest.builder().amenityIds(amenities).type(type).location(place).checkoutDate(checkoutDate).checkInDate(checkInDate).build(), pageNo, pageSize, sortBy);
            if(properties.isEmpty()) {
                return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
            }
        return ResponseUtil.customizeMapResponse(properties, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }


    @GetMapping(value = "/public/properties/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchByKeyword(@RequestParam("keyword") @NotBlank String keyword, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) @Min(0) int pageNo, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) @Min(1) int pageSize, @RequestParam(defaultValue = Constants.DEFAULT_PROPERTY_SORT_BY) @NotBlank String sortBy) {
        Map<String, Object> properties = propertyService.searchProperty(keyword, pageNo, pageSize, sortBy);
        if(properties == null) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeMapResponse(properties, HttpStatus.OK, Constants.SEARCH_LIST_RESULT);
    }

    @PreAuthorize("hasAuthority('owner') and #id == authentication.principal.id")
    @GetMapping(value = "/properties/owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllPropertiesOfOwner(@PathVariable @Min(1) int id, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) @Min(0) int pageNo, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) @Min(1) int pageSize, @RequestParam(defaultValue = Constants.DEFAULT_PROPERTY_SORT_BY) @NotBlank String sortBy) {
        Map<String, Object> map = propertyService.findAllPropertyOfOwner(id, pageNo, pageSize, sortBy);
        if(map == null) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeMapResponse(map, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }

    @PreAuthorize("hasAuthority('owner')")
    @PostMapping(value = "/properties", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addNew(@Valid @RequestBody ManagedPropertyRequest property) {
        ManagedPropertyResponse result = propertyService.save(property);
        return ResponseUtil.customizeResponse(result, HttpStatus.OK, Constants.ADD_RESOURCE_SUCCESS);
    }

    @PutMapping(value = "/properties/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@Valid @RequestBody ManagedPropertyRequest property, @PathVariable @Min(1) int id) {
        ManagedPropertyResponse result = propertyService.update(id, property);
        return ResponseUtil.customizeResponse(result,HttpStatus.OK, Constants.UPDATE_RESOURCE_SUCCESS);
    }

    @PreAuthorize("hasAuthority('owner')")
    @DeleteMapping(value = "/properties/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Min(1) int id) {
        propertyService.delete(id);
        return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.DELETE_RESOURCE_SUCCESS);
    }


    @PreAuthorize("hasAuthority('owner')")
    @PostMapping(value = "/properties/{id}/image")
    public ResponseEntity<Object> uploadBookImage(@RequestParam("image") @NotEmpty MultipartFile[] myFile, @PathVariable @Min(1) int id) {
        try {

            Arrays.asList(myFile).forEach(f -> {
                String  fileName = f.getOriginalFilename();
                propertyService.transferFile(f, imagePath, fileName);
                propertyService.uploadImage(fileName, id);

            });
            return ResponseUtil.customizeResponse(Constants.ADD_RESOURCE_SUCCESS, HttpStatus.OK, Constants.UPLOAD_IMAGE_SUCCESS);
        } catch (Exception e) {
            return ResponseUtil.customizeResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, Constants.UPLOAD_IMAGE_FAILED);
        }
    }

    @PreAuthorize("hasAuthority('owner')")
    @GetMapping(value = "/properties/{id}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findImagesOfProperty(@PathVariable @Min(1) int id) {
            return ResponseUtil.customizeResponse(propertyService.findImagesOfProperty(id), HttpStatus.OK, Constants.GET_RESOURCE_SUCCESS);
    }

    @PreAuthorize("hasAuthority('owner')")
    @DeleteMapping(value = "/properties/image/{id}")
    public ResponseEntity<Object> deleteImageOfProperty(@PathVariable @Min(1) int id) {
                propertyService.deleteImageOfProperty(id);
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.DELETE_RESOURCE_SUCCESS);
    }

}

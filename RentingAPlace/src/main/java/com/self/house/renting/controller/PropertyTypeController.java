package com.self.house.renting.controller;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.model.dto.response.TypeResponse;
import com.self.house.renting.service.PropertyTypeService;
import com.self.house.renting.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.PROPERTY_TYPE_API)
public class PropertyTypeController {
    private PropertyTypeService propertyTypeService;

    @Autowired
    public PropertyTypeController(PropertyTypeService propertyTypeService) {
        this.propertyTypeService = propertyTypeService;
    }

    @GetMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllType() {
        List<TypeResponse> types = propertyTypeService.findAll();
        if(types == null) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeResponse(types, HttpStatus.OK, Constants.SEARCH_LIST_RESULT);
    }

}

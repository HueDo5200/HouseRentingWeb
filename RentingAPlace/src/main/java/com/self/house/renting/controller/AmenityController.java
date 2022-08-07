package com.self.house.renting.controller;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.model.dto.response.AmenityResponse;
import com.self.house.renting.service.AmenityService;
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
@RequestMapping(Constants.AMENITY_API)
public class AmenityController {
    private AmenityService amenityService;
    @Autowired
    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllAmenity() {
        List<AmenityResponse> amenities = amenityService.findAll();
        if(amenities.isEmpty()) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeResponse(amenities, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }
}

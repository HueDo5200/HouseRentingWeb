package com.self.house.renting.service.serviceimpl;

import com.self.house.renting.model.dto.response.AmenityResponse;
import com.self.house.renting.model.entity.Amenity;
import com.self.house.renting.repository.AmenityRepository;
import com.self.house.renting.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmenityServiceImpl implements AmenityService {

    private AmenityRepository amenityRepository;

    @Autowired
    public AmenityServiceImpl(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Override
    public List<AmenityResponse> findAll() {
       List<Amenity> amenities = amenityRepository.findAll();
       if(amenities.isEmpty()) {
           return Collections.emptyList();
       }
       return getAmenityDtoList(amenities);
    }

    public List<AmenityResponse> getAmenityDtoList(List<Amenity> amenities) {
        return amenities.stream().map(Amenity::convertAmenityToDto).collect(Collectors.toList());
    }


}

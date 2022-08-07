package com.self.house.renting.service;

import com.self.house.renting.model.dto.response.AmenityResponse;

import java.util.List;

public interface AmenityService {
    List<AmenityResponse> findAll();
}

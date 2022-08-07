package com.self.house.renting.service;

import com.self.house.renting.model.dto.response.TypeResponse;


import java.util.List;

public interface PropertyTypeService  {
    List<TypeResponse> findAll();
}

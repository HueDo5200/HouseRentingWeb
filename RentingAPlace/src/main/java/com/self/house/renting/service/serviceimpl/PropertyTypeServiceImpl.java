package com.self.house.renting.service.serviceimpl;

import com.self.house.renting.model.dto.response.TypeResponse;
import com.self.house.renting.model.entity.PropertyType;
import com.self.house.renting.repository.PropertyTypeRepository;
import com.self.house.renting.service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PropertyTypeServiceImpl implements PropertyTypeService {

    private PropertyTypeRepository propertyTypeRepository;

    @Autowired
    public PropertyTypeServiceImpl(PropertyTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }

    @Override
    public List<TypeResponse> findAll() {
       List<PropertyType> types = propertyTypeRepository.findAll();
       if(types.isEmpty()) {
          return Collections.emptyList();
       }
        return getPropertyTypeDtoList(types);
    }

    public List<TypeResponse> getPropertyTypeDtoList(List<PropertyType> types) {
        return types.stream().map(PropertyType::convertPropertyTypeToDto).collect(Collectors.toList());
    }


}

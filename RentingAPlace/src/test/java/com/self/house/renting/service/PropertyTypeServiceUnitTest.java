package com.self.house.renting.service;

import com.self.house.renting.model.dto.response.TypeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PropertyTypeServiceUnitTest {

    @Autowired
    private PropertyTypeService propertyTypeService;

    @Test
    public void shouldReturnPropertyTypeList() {
        List<TypeResponse> responses = propertyTypeService.findAll();
        assertEquals(3, responses.size());
        assertEquals("flat", responses.get(0).getName());
        assertEquals("apartment", responses.get(1).getName());
        assertEquals("villa", responses.get(2).getName());
        assertEquals(1, responses.get(0).getId());
        assertEquals(2, responses.get(1).getId());
        assertEquals(3, responses.get(2).getId());
    }
}

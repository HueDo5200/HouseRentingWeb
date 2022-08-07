package com.self.house.renting.service;


import com.self.house.renting.model.dto.response.AmenityResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AmenityServiceUnitTest {

    Logger logger = LoggerFactory.getLogger(AmenityServiceUnitTest.class);

    @Autowired
    private AmenityService amenityService;

    List<AmenityResponse> responses;

    @Test
    public void shouldReturnNonEmptyAmenityList() {
        List<AmenityResponse> amenityResponses = amenityService.findAll();
        assertEquals(3, amenityResponses.size());
        assertEquals("pool facing", amenityResponses.get(0).getName());
        assertEquals(1, amenityResponses.get(0).getId());
        assertEquals("beach facing", amenityResponses.get(1).getName());
        assertEquals(2, amenityResponses.get(1).getId());
        assertEquals("wifi", amenityResponses.get(2).getName());
        assertEquals(3, amenityResponses.get(2).getId());

    }




}

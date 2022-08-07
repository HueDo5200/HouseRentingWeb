package com.self.house.renting.service;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.exception.ResourceNotFoundException;
import com.self.house.renting.model.dto.response.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @Test
    public void shouldReturnUserWhenFindingByName() {
        assertTrue(userService.findByUsername("owner").isPresent());
    }

    @Test
    public void shouldReturnNothingWhenFindingNotExistedUserByName() {
        assertTrue(userService.findByUsername("yaya").isEmpty());
    }

    @Test
    public void shouldReturnUserWhenFindingByExistedId() {
        UserResponse actual = userService.findById(2);
        UserResponse expected = UserResponse.builder().id(2).username("owner")
                .email("owner@gmail.com")
                .phoneNumber("0334455555").build();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenFindUserByNonExistedId() {
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(23);
        }, Constants.RESOURCE_NOT_FOUND);
        Assertions.assertEquals(Constants.RESOURCE_NOT_FOUND, ex.getMessage());
    }
}

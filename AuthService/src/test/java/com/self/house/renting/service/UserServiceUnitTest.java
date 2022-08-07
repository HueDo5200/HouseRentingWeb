package com.self.house.renting.service;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.exception.ResourceExistedException;
import com.self.house.renting.model.dto.request.RegisterRequest;
import com.self.house.renting.model.dto.response.RegisterResponse;
import com.self.house.renting.model.dto.response.RoleResponse;
import com.self.house.renting.model.entity.Role;
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
    public void shouldThrowResourceExistedWhenRegisterWithExistedUsername() {
        RegisterRequest request = RegisterRequest.builder()
                .username("owner")
                .lastName("Thanh")
                .firstName("Van")
                .email("thien@gmail.com")
                .password("$2a$12$NBIaiXL1t0UvYRyBQ7zPyO9O.4m3GtAjc4eLAm3YZYKKADlz5aY.q")
                .phone("044344444")
                .role(Role.builder().id(1).name("owner").build())
                .build();
        ResourceExistedException ex = Assertions.assertThrows(ResourceExistedException.class, () -> {
            userService.register(request);
        }, Constants.REGISTER_FAILED);
        Assertions.assertEquals(Constants.REGISTER_FAILED, ex.getMessage());
    }

    @Test
    public void shouldReturnUserResponseWhenRegisterSucceed() {

        RegisterRequest newUser = RegisterRequest.builder()
                .username("Thien")
                .lastName("Thanh")
                .firstName("Van")
                .email("thien@gmail.com")
                .password("$2a$12$NBIaiXL1t0UvYRyBQ7zPyO9O.4m3GtAjc4eLAm3YZYKKADlz5aY.q")
                .phone("044344444")
                .role(Role.builder().id(2).name("customer").build())
                .build();
        RegisterResponse expected = RegisterResponse.builder().username("Thien")
                .email("thien@gmail.com")
                .role(RoleResponse.builder().id(2).name("customer").build())
                .build();
        RegisterResponse actual = userService.register(newUser);
        assertEquals(expected.getRole(), actual.getRole());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getEmail(), actual.getEmail());
    }
}

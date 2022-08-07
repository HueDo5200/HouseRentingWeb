package com.self.house.renting.service;

import com.self.house.renting.model.dto.request.RegisterRequest;
import com.self.house.renting.model.dto.response.RegisterResponse;
import com.self.house.renting.model.entity.Users;

import java.util.Optional;

public interface UserService {

    Optional<Users> findByUsername(String username);
    RegisterResponse register(RegisterRequest request);

}

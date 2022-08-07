package com.self.house.renting.service;

import com.self.house.renting.model.dto.response.UserResponse;
import com.self.house.renting.model.entity.Users;

import java.util.Optional;

public interface UserService {

    Optional<Users> findByUsername(String username);
    UserResponse findById(int id);

}

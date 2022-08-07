package com.self.house.renting.service.serviceimpl;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.exception.ResourceExistedException;
import com.self.house.renting.model.dto.request.RegisterRequest;
import com.self.house.renting.model.dto.response.RegisterResponse;
import com.self.house.renting.model.dto.response.UserResponse;
import com.self.house.renting.model.entity.Users;
import com.self.house.renting.repository.UserRepository;
import com.self.house.renting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
           throw new ResourceExistedException(Constants.REGISTER_FAILED);
        }
        Users user = userRepository.save(request.getUserFromRegisterRequest());
        return user.getRegisterResponseFromUser();
    }

    public UserResponse getCustomerDto(Users user) {
        return UserResponse.builder().id(user.getId()).username(user.getUsername()).phoneNumber(user.getPhone()).email(user.getEmail()).build();
    }





}

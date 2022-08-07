package com.self.house.renting.controller;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.service.UserService;
import com.self.house.renting.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Validated
@RestController@RequestMapping(Constants.USER_API)
public class UserController {
    private UserService customerService;

    @Autowired
    public UserController(UserService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findCustomerById(@PathVariable @Min(1) int id) {
        return ResponseUtil.customizeResponse(customerService.findById(id), HttpStatus.OK, Constants.GET_RESOURCE_SUCCESS);
    }
}

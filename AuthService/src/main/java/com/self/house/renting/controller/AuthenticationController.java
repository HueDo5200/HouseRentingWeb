package com.self.house.renting.controller;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.model.dto.request.LoginRequest;
import com.self.house.renting.model.dto.request.RegisterRequest;
import com.self.house.renting.model.dto.response.LoginResponse;
import com.self.house.renting.model.dto.response.RegisterResponse;
import com.self.house.renting.model.dto.response.RoleResponse;
import com.self.house.renting.security.TokenUtil;
import com.self.house.renting.security.service.UserDetailsImpl;
import com.self.house.renting.service.UserService;
import com.self.house.renting.util.PasswordUtil;
import com.self.house.renting.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Constants.AUTH_API)
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

	private TokenUtil tokenUtil;

	private UserDetailsService userDetailsService;

	private UserService userService;

	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager, TokenUtil tokenUtil, UserDetailsService userDetailsService, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.tokenUtil = tokenUtil;
		this.userDetailsService = userDetailsService;
		this.userService = userService;
	}

	@PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest login) throws Exception {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch(DisabledException e) {
			throw new Exception(Constants.USER_DISABLED);
		} catch(BadCredentialsException e) {
			return ResponseUtil.customizeResponse(null, HttpStatus.UNAUTHORIZED, Constants.WRONG_AUTHENTICATION);
		}
		UserDetailsImpl details = (UserDetailsImpl) userDetailsService.loadUserByUsername(login.getUsername());
		String jwt = tokenUtil.generateJwtToken(details);
		LoginResponse userDto = LoginResponse.builder().id(details.getId()).username(details.getUsername()).email(details.getEmail()).phone(details.getPhone()).jwtToken(jwt)
                .role(RoleResponse.builder().id(details.getRole().getId()).name(details.getRole().getName()).build()).build();
		return ResponseUtil.customizeResponse(userDto, HttpStatus.OK, Constants.LOGIN_SUCCESS);

    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request) {
        request.setPassword(PasswordUtil.encryptPassword(request.getPassword()));
			RegisterResponse response = userService.register(request);
			return ResponseUtil.customizeResponse(response, HttpStatus.OK, Constants.REGISTER_SUCCESS);
    }

}

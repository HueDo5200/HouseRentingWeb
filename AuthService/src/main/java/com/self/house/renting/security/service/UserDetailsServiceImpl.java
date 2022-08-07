package com.self.house.renting.security.service;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.exception.ResourceNotFoundException;
import com.self.house.renting.model.entity.Users;
import com.self.house.renting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService customerService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users existedUser = customerService.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(existedUser.getRole().getName());
		authorities.add(authority);
		return new UserDetailsImpl(existedUser.getId(), existedUser.getUsername(), existedUser.getPassword(), existedUser.getEmail(),
				existedUser.getPhone(), existedUser.getRole(),
				authorities);
	}

}

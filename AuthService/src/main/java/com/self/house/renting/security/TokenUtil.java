package com.self.house.renting.security;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.security.service.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

	@Value("${secret}")
	private String secret;

	@Value("${timeout}")
	private int timeout;

	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public String getUsernameFromToken(String token) {
		return getClaims(token).getSubject();
	}

	public Date getExpirationDate(String token) {
		return getClaims(token).getExpiration();
	}

	public boolean isTokenExpired(String token) {
		return getExpirationDate(token).before(new Date());
	}

	public String generateJwtToken(UserDetailsImpl details) {
		Map<String, Object> map = new HashMap<>();
		map.put(Constants.DETAILS_USERNAME, details.getUsername());
		map.put(Constants.DETAILS_ROLE, details.getRole().getName());
		map.put(Constants.DETAILS_ID, details.getId());
		map.put(Constants.DETAILS_EMAIL, details.getEmail());
		return Jwts.builder().setClaims(map).setSubject(details.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + timeout))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return !isTokenExpired(token);
		} catch (Exception e) {
			return false;
		}
	}

}

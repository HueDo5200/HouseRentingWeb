package com.self.house.renting.security;

import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.hcl.group5.rentingaplace.constants.Constants;
//import com.hcl.group5.rentingaplace.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

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

	public boolean validateToken(String token) {
		try {
			if(token.isEmpty()) {
				return false;
			}
			getClaims(token);
			return !isTokenExpired(token);
		} catch (Exception e) {
			return false;
		}
	}

}

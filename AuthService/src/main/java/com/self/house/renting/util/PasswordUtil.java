package com.self.house.renting.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {
	public static String encryptPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}

}

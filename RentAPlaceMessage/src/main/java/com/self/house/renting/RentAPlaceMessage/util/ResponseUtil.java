package com.self.house.renting.RentAPlaceMessage.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static ResponseEntity<Object> customizeResponse(Object data, HttpStatus status, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }
}

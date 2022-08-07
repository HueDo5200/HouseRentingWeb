package com.self.house.renting.util;

import com.self.house.renting.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static ResponseEntity<Object> customizeResponse(Object data, HttpStatus status, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.DATA_KEY, data);
        map.put(Constants.MESSAGE_KEY, message);
        return new ResponseEntity<>(map, status);
    }
    public static ResponseEntity<Object> customizeMapResponse(Map<String, Object> data, HttpStatus status, String message) {
        data.put(Constants.MESSAGE_KEY, message);
        return new ResponseEntity<>(data, status);
    }
}

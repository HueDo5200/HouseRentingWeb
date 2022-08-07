package com.self.house.renting.RentAPlaceMessage.controller;

import com.self.house.renting.RentAPlaceMessage.constants.Constants;
import com.self.house.renting.RentAPlaceMessage.exception.PageOutOfBoundException;
import com.self.house.renting.RentAPlaceMessage.exception.ResourceNotFoundException;
import com.self.house.renting.RentAPlaceMessage.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> throwClassNotFoundException(String message) {
        return ResponseUtil.customizeResponse(null, HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> throwServerException() {
        return ResponseUtil.customizeResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, Constants.SERVER_ERROR);
    }

    @ExceptionHandler(value = PageOutOfBoundException.class)
    public ResponseEntity<Object> handlePageOfOutBoundException() {
        return ResponseUtil.customizeResponse(null, HttpStatus.BAD_REQUEST, Constants.PAGE_OUT_OF_BOUND_EXCEPTION);
    }
}

package com.self.house.renting.exception;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> throwClassNotFoundException(ResourceNotFoundException ex) {
        return ResponseUtil.customizeResponse(null, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> throwServerException(Exception e) {
        return ResponseUtil.customizeResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<Object> throwUnauthorizedException(UnauthorizedException ex) {
        return ResponseUtil.customizeResponse(null, HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(value = InvalidDateRangeException.class)
    public ResponseEntity<Object> throwServerException(InvalidDateRangeException e) {
        return ResponseUtil.customizeResponse(null, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = ResourceUpdatingFailedException.class)
    public ResponseEntity<Object> throwResourceUpdateException(Exception e) {
        return ResponseUtil.customizeResponse(null, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = ReservationAlreadyExistedException.class)
    public ResponseEntity<Object> throwReservationAlreadyExistedException(Exception e) {
        return ResponseUtil.customizeResponse(null, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
         exception.getBindingResult().getAllErrors().forEach(e -> {
             errors.put(((FieldError) e).getField(), e.getDefaultMessage());
         });
         return ResponseUtil.customizeResponse(errors, HttpStatus.BAD_REQUEST, Constants.VALIDATION_ERROR);
    }

    @ExceptionHandler(value = PageOutOfBoundException.class)
    public ResponseEntity<Object> handlePageOfOutBoundException(PageOutOfBoundException ex) {
        return ResponseUtil.customizeResponse(null, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = EmailSendingFailedException.class)
    public ResponseEntity<Object> handleEmailSendingFailedException(PageOutOfBoundException ex) {
        return ResponseUtil.customizeResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}

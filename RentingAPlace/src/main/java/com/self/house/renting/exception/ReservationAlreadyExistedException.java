package com.self.house.renting.exception;

public class ReservationAlreadyExistedException extends RuntimeException {
    public ReservationAlreadyExistedException(String message) {
        super(message);
    }
}

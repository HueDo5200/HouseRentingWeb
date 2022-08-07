package com.self.house.renting.exception;

public class EmailSendingFailedException extends  RuntimeException{
    public EmailSendingFailedException(String message) {
        super(message);
    }
}

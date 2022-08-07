package com.self.house.renting.service;

import com.self.house.renting.model.dto.request.ReservationRequest;
import com.self.house.renting.model.entity.Email;

public interface EmailService {
    void sendEmail(Email email);
    Email initializeEmail(String appEmail, ReservationRequest request);
}

package com.self.house.renting.service;

import com.self.house.renting.model.dto.request.ReservationRequest;
import com.self.house.renting.model.dto.response.ReservationResponse;

import java.util.Map;

public interface ReservationService {
    Map<String, Object> getReservationResponseOfOwner(int ownerId, int pageNo, int pageSize, String sortBy);
    Map<String, Object> viewReservation(int ownerId, int pageNo, int pageSize, String sortBy);
    void updateReservationStatus(int status, int reservationId);

    ReservationResponse reserveProperty(ReservationRequest reservation);
    Map<String, Object> findReservationHistoryOfCustomer(int id, int pageNo, int pageSize, String sortBy);
}

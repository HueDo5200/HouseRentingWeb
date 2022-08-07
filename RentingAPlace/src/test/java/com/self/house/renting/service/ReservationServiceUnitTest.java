package com.self.house.renting.service;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.exception.InvalidDateRangeException;
import com.self.house.renting.exception.ReservationAlreadyExistedException;
import com.self.house.renting.exception.ResourceNotFoundException;
import com.self.house.renting.model.dto.request.ReservationRequest;
import com.self.house.renting.model.dto.response.ReservationResponse;
import com.self.house.renting.model.entity.Reservation;
import com.self.house.renting.repository.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ReservationServiceUnitTest {

    Logger logger = LoggerFactory.getLogger(ReservationServiceUnitTest.class);
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;



    @Test
    public void shouldReturnCurrentReservationOfOwner() {
         Map<String, Object> actual = reservationService.viewReservation(5, 0, 1, "startDate");
         Map<String, Object> expected = new HashMap<>();
        List<ReservationResponse> reservations = new ArrayList<>();
        ReservationResponse reservationResponse = ReservationResponse.builder()
                        .id(29).customerId(1)
                       .startDate(LocalDateTime.of(2022, 5, 26, 0, 0, 0))
                        .endDate(LocalDateTime.of(2022, 10, 28, 0, 0, 0))
                                .status(0)
                                        .total(303).build();
        reservations.add(reservationResponse);
         expected.put("data", reservations);
        expected.put("currentPage", 0);
        expected.put("totalPages", 1);
        expected.put("itemsNumber", 1L);
      assertEquals(expected.size(), actual.size());
     assertEquals(expected.get("currentPage"), actual.get("currentPage"));
     assertEquals(expected.get("totalPages"), actual.get("totalPages"));
     assertEquals(expected.get("itemsNumber"), actual.get("itemsNumber"));
    }

//    @Test
//    public void shouldThrowUnauthorizedExceptionIfNotOwner() {
//       UnauthorizedException ex = Assertions.assertThrows(UnauthorizedException.class, () -> {
//        reservationService.viewReservation(3, 0, 1, "startDate");
//        }, Constants.NOT_OWNER);
//        Assertions.assertEquals(Constants.NOT_OWNER, ex.getMessage());
//    }

//    @Test
//    public void shouldThrowResourceNotFoundExceptionIfUserNotExisted() {
//        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//            reservationService.viewReservation(20, 0, 1, "startDate");
//        }, Constants.RESOURCE_NOT_FOUND);
//        Assertions.assertEquals(Constants.RESOURCE_NOT_FOUND, ex.getMessage());
//    }


    @Test
    public void shouldReturnEmptyMapIfNoReservation() {
        Map<String, Object> actual = reservationService.viewReservation(6, 0, 1, "startDate");
       assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldReturnReservationSaleList() {
        Map<String, Object> actual = reservationService.getReservationResponseOfOwner(2, 0, 1, "startDate");
        Map<String, Object> expected = new HashMap<>();
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        ReservationResponse reservationResponse1 = ReservationResponse.builder()
                .id(1).customerId(1)
                .startDate(LocalDateTime.of(2022, 4, 26, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, 4, 30, 0, 0, 0))
                .status(2)
                .total(505).build();
        ReservationResponse reservationResponse2 = ReservationResponse.builder()
                .id(2).customerId(1)
                .startDate(LocalDateTime.of(2022, 5, 17, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, 12, 12, 0, 0, 0))
                .status(2)
                .total(1000).build();
        reservationResponses.add(reservationResponse1);
        reservationResponses.add(reservationResponse2);
        expected.put("data", reservationResponses);
        expected.put("currentPage", 0);
        expected.put("totalPages", 2);
        expected.put("itemsNumber", 2L);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get("currentPage"), actual.get("currentPage"));
        assertEquals(expected.get("totalPages"), actual.get("totalPages"));
        assertEquals(expected.get("itemsNumber"), actual.get("itemsNumber"));

    }

    @Test
    public void shouldReturnEmptyHashMapOfReservationSale() {
        Map<String, Object> actual = reservationService.getReservationResponseOfOwner(5, 0, 1, "startDate");
        assertTrue(actual.isEmpty());
    }


    @Test
    public void shouldThrowResourceNotFoundExceptionIfUserNotExistedForGetReservationSales() {
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.getReservationResponseOfOwner(20, 0, 1, "startDate");
        }, Constants.RESOURCE_NOT_FOUND);
        Assertions.assertEquals(Constants.RESOURCE_NOT_FOUND, ex.getMessage());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionIfReservationNotExisted() {
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.updateReservationStatus(1, 10);
        }, Constants.RESOURCE_NOT_FOUND);
        Assertions.assertEquals(Constants.RESOURCE_NOT_FOUND, ex.getMessage());
    }

    @Test
    public void shouldReturnNothingWhenUpdateReservationStatus() {
        Reservation reservationBefore = reservationRepository.findById(27).orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));

        reservationService.updateReservationStatus(1, 27);
        Reservation reservationAfter = reservationRepository.findById(27).orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));

       assertNotEquals(reservationBefore.getStatus(), reservationAfter.getStatus());
       assertEquals(1, reservationAfter.getStatus());
    }

    @Test
    public void shouldReturnReservationHistoryOfCustomer() {
            Map<String, Object> actual = reservationService.findReservationHistoryOfCustomer(4, 0, 1, "startDate");
            Map<String, Object> expected = new HashMap<>();
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        ReservationResponse reservationResponse1 = ReservationResponse.builder()
                .id(27).customerId(4)
               .startDate(LocalDateTime.of(2022, 5, 16, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, 5, 22, 0, 0, 0))
                .status(0)
                .total(505).build();
        ReservationResponse reservationResponse2 = ReservationResponse.builder()
                .id(28).customerId(4)
                .startDate(LocalDateTime.of(2022, 12, 16, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, 12, 20, 0, 0, 0))
                .status(1)
                .total(404).build();
        reservationResponses.add(reservationResponse1);
        reservationResponses.add(reservationResponse2);
        expected.put("data", reservationResponses);
        expected.put("currentPage", 0);
        expected.put("totalPages", 2);
        expected.put("itemsNumber", 2L);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get("currentPage"), actual.get("currentPage"));
        assertEquals(expected.get("totalPages"), actual.get("totalPages"));
        assertEquals(expected.get("itemsNumber"), actual.get("itemsNumber"));

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionIfUserNotExisted() {
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.findReservationHistoryOfCustomer(25, 0, 1, "startDate");
        }, Constants.RESOURCE_NOT_FOUND);
        Assertions.assertEquals(Constants.RESOURCE_NOT_FOUND, ex.getMessage());
    }

    @Test
    public void shouldThrowInvalidDateRangeExceptionWhenReservePropertyWithInvalidDate() {
        ReservationRequest request = ReservationRequest.builder()
                .propertyId(1)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .email("thanh@gmail.com")
                .build();
        InvalidDateRangeException ex = Assertions.assertThrows(InvalidDateRangeException.class, () -> {
            reservationService.reserveProperty(request);
        }, Constants.INVALID_DATE_RANGE);
        Assertions.assertEquals(Constants.INVALID_DATE_RANGE, ex.getMessage());
    }

    @Test
    public void shouldThrowReservationAlreadyExistedExceptionWhenReservePropertyWithInvalidDate() {
        ReservationRequest request1 = ReservationRequest.builder()
                .propertyId(3)
                .startDate(LocalDateTime.of(2022, 5, 20, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, 10, 26, 0, 0, 0))
                .email("thanh@gmail.com")
                .build();
        ReservationRequest request2 = ReservationRequest.builder()
                .propertyId(3)
                .startDate(LocalDateTime.of(2022, 10, 28, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, 10, 30, 0, 0, 0))
                .email("thanh@gmail.com")
                .build();

        ReservationRequest request3 = ReservationRequest.builder()
                .propertyId(3)
                .startDate(LocalDateTime.of(2022, 5, 22, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, 10, 30, 0, 0, 0))
                .email("thanh@gmail.com")
                .build();

        ReservationAlreadyExistedException ex1 = Assertions.assertThrows(ReservationAlreadyExistedException.class, () -> {
            reservationService.reserveProperty(request1);
        }, Constants.PROPERTY_RESERVED);

        ReservationAlreadyExistedException ex2 = Assertions.assertThrows(ReservationAlreadyExistedException.class, () -> {
            reservationService.reserveProperty(request2);
        }, Constants.PROPERTY_RESERVED);

        ReservationAlreadyExistedException ex3 = Assertions.assertThrows(ReservationAlreadyExistedException.class, () -> {
            reservationService.reserveProperty(request3);
        }, Constants.PROPERTY_RESERVED);

        Assertions.assertEquals(Constants.PROPERTY_RESERVED, ex1.getMessage());
        Assertions.assertEquals(Constants.PROPERTY_RESERVED, ex2.getMessage());
        Assertions.assertEquals(Constants.PROPERTY_RESERVED, ex3.getMessage());
    }

    @Test
    public void shouldReturnReservationResponseIfReservationSuccess() {
        ReservationResponse expected = ReservationResponse.builder()
                .id(30)
                .startDate(LocalDateTime.of(2022, 11, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, 11, 3, 0, 0, 0))
                .customerId(1)
                .status(0)
                .total(303)
                .build();

        ReservationRequest request = ReservationRequest.builder()
                .startDate(LocalDateTime.of(2022, 11, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, 11, 3, 0, 0, 0))
                .userId(1)
                .propertyId(3)
                .total(303)
                .email("owner@gmail.com")
                .build();
       ReservationResponse actual = reservationService.reserveProperty(request);
       assertEquals(expected.getId(), actual.getId());
       assertEquals(expected.getStatus(), actual.getStatus());
       assertEquals(expected.getEndDate(), actual.getEndDate());
       assertEquals(expected.getStartDate(), actual.getStartDate());
       assertEquals(expected.getTotal(), actual.getTotal());
       assertEquals(expected.getCustomerId(), actual.getCustomerId());
    }





}

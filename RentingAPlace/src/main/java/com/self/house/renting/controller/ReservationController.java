package com.self.house.renting.controller;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.model.dto.request.ReservationRequest;
import com.self.house.renting.model.dto.response.ReservationResponse;
import com.self.house.renting.model.dto.response.ReservationStatusResponse;
import com.self.house.renting.model.entity.Email;
import com.self.house.renting.service.UserService;
import com.self.house.renting.service.EmailService;
import com.self.house.renting.service.ReservationService;
import com.self.house.renting.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Validated
@RestController@RequestMapping(Constants.RESERVATION_API)
public class ReservationController {

    private UserService userService;
    private EmailService emailService;
    private ReservationService reservationService;
    Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Value("${spring.mail.username}")
    private String appEmail;

    @Autowired
    public ReservationController(UserService userService, EmailService emailService, ReservationService reservationService) {
        this.userService = userService;
        this.emailService = emailService;
       this.reservationService = reservationService;
    }

    @PreAuthorize("hasAuthority('customer') and authentication.principal.id == #id")
    @GetMapping(value = "/history/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> viewPastReservation(@PathVariable @Min(1) int id, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) @Min(0) int pageNo, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) @Min(1) int pageSize, @RequestParam(defaultValue = Constants.DEFAULT_RESERVATION_SORT_BY) @NotBlank String sortBy) {
        Map<String, Object> reservations = reservationService.findReservationHistoryOfCustomer(id, pageNo, pageSize, sortBy);
        if(reservations.isEmpty()) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeMapResponse(reservations, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }

    @PreAuthorize("hasAuthority('customer')")
    @PostMapping(value = "/property", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> reserveProperty(@Valid @RequestBody ReservationRequest request) {
        ReservationResponse res = reservationService.reserveProperty(request);
        Email email = emailService.initializeEmail(appEmail, request);
        emailService.sendEmail(email);
        return ResponseUtil.customizeResponse(res, HttpStatus.OK, Constants.RESERVE_SUCCESS);
    }


    @PreAuthorize("hasAuthority('owner') and authentication.principal.id == #id")
    @GetMapping(value = "/sale/owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> viewReservationSale(@PathVariable @Min(1) int id, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) @Min(0) int pageNo, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) @Min(1) int pageSize, @RequestParam(defaultValue = Constants.DEFAULT_RESERVATION_SORT_BY) @NotBlank String sortBy) {
        Map<String, Object> reservations = reservationService.getReservationResponseOfOwner(id, pageNo, pageSize, sortBy);
        if(reservations == null) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeMapResponse(reservations, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }


       @PreAuthorize("hasAuthority('owner') and authentication.principal.id == #id")
    @GetMapping(value = "/owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> viewReservation(@PathVariable @Min(1) int id, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) @Min(0) int pageNo, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) @Min(1) int pageSize, @RequestParam(defaultValue = Constants.DEFAULT_RESERVATION_SORT_BY) @NotBlank String sortBy) {
        Map<String, Object> reservations = reservationService.viewReservation(id, pageNo, pageSize, sortBy);
        if(reservations == null) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.NO_RESERVATION);
        }
        return ResponseUtil.customizeMapResponse(reservations, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }

    @PreAuthorize("hasAuthority('owner')")
    @PutMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateReservationStatus(@Valid @RequestBody ReservationStatusResponse status) {
        this.reservationService.updateReservationStatus(status.getStatus(), status.getReservationId());
        return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.UPDATE_RESOURCE_SUCCESS);
    }


}

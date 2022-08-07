package com.self.house.renting.service.serviceimpl;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.exception.InvalidDateRangeException;
import com.self.house.renting.exception.PageOutOfBoundException;
import com.self.house.renting.exception.ReservationAlreadyExistedException;
import com.self.house.renting.exception.ResourceNotFoundException;
import com.self.house.renting.model.dto.request.ReservationRequest;
import com.self.house.renting.model.dto.response.ReservationResponse;
import com.self.house.renting.model.entity.Property;
import com.self.house.renting.model.entity.Reservation;
import com.self.house.renting.repository.ReservationRepository;
import com.self.house.renting.repository.UserRepository;
import com.self.house.renting.repository.custom.ReservationCustomRepository;
import com.self.house.renting.repository.specification.ReservationSpecification;
import com.self.house.renting.service.ReservationService;
import com.self.house.renting.util.DtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private ReservationCustomRepository reservationCustomRepository;
    private UserRepository userRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationCustomRepository reservationCustomRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationCustomRepository = reservationCustomRepository;
        this.userRepository = userRepository;
    }


    /**
     * @param request not null
     * @return reservation response
     * @throws InvalidDateRangeException          if check in date is the same day or after check out date
     * @throws ReservationAlreadyExistedException if the property already reserved for the time request
     */
    @Override
    public ReservationResponse reserveProperty(ReservationRequest request) {
        if (request.getStartDate().compareTo(request.getEndDate()) >= 0) {
            throw new InvalidDateRangeException(Constants.INVALID_DATE_RANGE);
        }
        List<Reservation> reservations = reservationRepository.findByProperty(Property.builder().id(request.getPropertyId()).build());
        if (!isValidReservationRequest(reservations, request)) {
            throw new ReservationAlreadyExistedException(Constants.PROPERTY_RESERVED);
        }
        Reservation reservation = request.getReservationFromDto();
        reservation.setStatus(Constants.WAITING_RESERVATION_STATUS);
        Reservation result = reservationRepository.save(reservation);
        return result.getReservationResponseFromDto();
    }


    /**
     * @param ownerId  > 0
     * @param pageNo   >= 0
     * @param pageSize > 0
     * @param sortBy   not null or empty
     * @return map of reservation information and paging, sorting data
     */
    @Override
    public Map<String, Object> viewReservation(int ownerId, int pageNo, int pageSize, String sortBy) {
        throwResourceNotFoundException(ownerId);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Reservation> pagedResults = reservationRepository.findAll(ReservationSpecification.hasCurrentReservation(ownerId), paging);
        return populatePagingReservationMap(pagedResults, pageNo);
    }

    /**
     * @param ownerId  > 0
     * @param pageNo   >= 0
     * @param pageSize > 0
     * @param sortBy   not null or empty
     * @return reservations by customer
     */
    @Override
    public Map<String, Object> getReservationResponseOfOwner(int ownerId, int pageNo, int pageSize, String sortBy) {
        throwResourceNotFoundException(ownerId);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Reservation> pagedResults = reservationRepository.findAll(ReservationSpecification.hasReservationSaleOfOwner(ownerId), paging);
        return populatePagingReservationMap(pagedResults, pageNo);
    }

    /**
     * @param status        >= 0
     * @param reservationId > 0
     */
    @Override
    public void updateReservationStatus(int status, int reservationId) {
        reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));
        reservationCustomRepository.updateReservationStatus(status, reservationId);
    }


    /**
     * @param list    not null or empty
     * @param request not null
     * @return if the there is a current reservation that exists for the request reservation time
     * return false
     * else
     * return true
     */
    public boolean isValidReservationRequest(List<Reservation> list, ReservationRequest request) {
        LocalDate startDateOfRequest = request.getStartDate().toLocalDate();
        LocalDate endDateOfRequest = request.getEndDate().toLocalDate();
        for (Reservation reservation : list) {
            LocalDate endDateOfReservation = reservation.getEndDate().toLocalDate();
            LocalDate startDateOfReservation = reservation.getStartDate().toLocalDate();
            if (startDateOfRequest.compareTo(endDateOfReservation) <= 0 && startDateOfRequest.compareTo(startDateOfReservation) >= 0
                    || endDateOfRequest.compareTo(startDateOfReservation) >= 0 && endDateOfRequest.compareTo(endDateOfReservation) <= 0
                    || startDateOfRequest.compareTo(startDateOfReservation) <= 0 && endDateOfRequest.compareTo(endDateOfReservation) >= 0
            ) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param pageNo   >= 0
     * @param pageSize > 0
     * @param sortBy   not null or empty
     * @return map of past reservations of customer and paging data
     */
    @Override
    public Map<String, Object> findReservationHistoryOfCustomer(int userId, int pageNo, int pageSize, String sortBy) {
        throwResourceNotFoundException(userId);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Reservation> pagedResult = reservationRepository.findAll(ReservationSpecification.hasReservationOfCustomer(userId), paging);
        return populatePagingReservationMap(pagedResult, pageNo);

    }


    /**
     * @param pagedResults not null
     * @param pageNo       >= 0
     * @return if pageResults has not content
     * return empty map
     * else
     * if get current is true
     * return map of current reservation and paging data
     * else
     * return map of past reservation and paging data
     * @throws PageOutOfBoundException if pageNo >= number of pages
     */
    public Map<String, Object> populatePagingReservationMap(Page<Reservation> pagedResults, int pageNo) {
        if (pagedResults.isEmpty()) {
           return new HashMap<>();
        }
        List<Reservation> list = pagedResults.getContent();
        List<ReservationResponse> responses = DtoUtil.toReservationResponse(list);
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.DATA_KEY, responses);
        map.put(Constants.CURRENT_PAGE_KEY, pagedResults.getNumber());
        map.put(Constants.TOTAL_PAGE_KEY, pagedResults.getTotalPages());
        map.put(Constants.ITEMS_NUMBER_KEY, pagedResults.getTotalElements());
        return map;
    }


    public void throwResourceNotFoundException(int userId) {
       userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));
    }



}

package com.self.house.renting.repository.custom;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.exception.InvalidDateRangeException;
import com.self.house.renting.model.dto.request.SearchCriteriaRequest;
import com.self.house.renting.util.DateUtil;
import com.self.house.renting.util.DtoUtil;
import com.self.house.renting.model.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PropertyCustomRepository {

    private Logger logger = LoggerFactory.getLogger(PropertyCustomRepository.class);

    @PersistenceContext
    EntityManager manager;

    /**
     *
     * @param criteria not null, check in date, check out date must be after current date,
     *                 check in date must be before check out date
     * @param pageNo >= 0
     * @param pageSize > 0
     * @param sortBy not null or empty
     * @throws InvalidDateRangeException if the check in is after or equal check out date
     * @return
     *          join different table with criteria api
     *          use Predicate for dynamic query with optional conditions
     *          the result of executed query is a list filtered by location, type, amenity
     *          filter the list to get the ongoing reservation
     *          if the result is null or empty
     *              return map of list
     *          else
     *              return the map of the result
     */
    public Map<String, Object> searchPropertyByCriteria(SearchCriteriaRequest criteria, int pageNo, int pageSize, String sortBy) {
        List<Integer> amenityIds = criteria.getAmenityIds();
        System.out.println("menity " + amenityIds);
        String type = criteria.getType();
        String location = criteria.getLocation();
        LocalDateTime inDate = DateUtil.getTimeZoneDatetimeFromZone(criteria.getCheckInDate(), Constants.VST);
        LocalDateTime outDate = DateUtil.getTimeZoneDatetimeFromZone(criteria.getCheckoutDate(), Constants.VST);
        handleDateException(inDate, outDate);
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Property> cq = cb.createQuery(Property.class);
        Root<Property> rootProperty = cq.from(Property.class);
        Join<Property, Location> plJoin = rootProperty.join(Constants.PROPERTY_LOCATION_ATTRIBUTE);
        Join<Property, PropertyType> ppJoin = rootProperty.join(Constants.PROPERTY_PROPERTY_TYPE_ATTRIBUTE);
       // Join<Property, Reservation> prJoin = rootProperty.join(Constants.PROPERTY_RESERVATIONS_ATTRIBUTE);
        //Join<Property, Amenity> paJoin = rootProperty.join(Constants.PROPERTY_AMENITIES_ATTRIBUTE);

        List<Predicate> conditions = new ArrayList<>();
        if (location != null && !location.isEmpty() && !location.equalsIgnoreCase(Constants.DEFAULT_LOCATION_VALUE)) {
            conditions.add(cb.equal(plJoin.get(Constants.LOCATION_CITY_ATTRIBUTE), location));
        }
        if (type != null && !type.isEmpty() && !type.equalsIgnoreCase(Constants.DEFAULT_TYPE_VALUE)) {
            conditions.add(cb.equal(ppJoin.get(Constants.PROPERTY_TYPE_NAME_ATTRIBUTE), type));
        }
        System.out.println("conditions " + conditions.size());
//        List<Predicate> predicateList = new ArrayList<>();
//        if(amenityIds != null && !amenityIds.isEmpty()) {
//            amenityIds.forEach(e -> {
//                predicateList.add(cb.equal(paJoin.get(Constants.AMENITY_ID_ATTRIBUTE), e));
//            });
//        }
//        Predicate andAmenityPredicate = cb.or(predicateList.toArray(new Predicate[]{}));
//        conditions.add(andAmenityPredicate);

//        if (amenity != null && !amenity.isEmpty() && !amenity.equalsIgnoreCase(Constants.DEFAULT_AMENITY_VALUE)) {
//            conditions.add(cb.equal(paJoin.get(Constants.AMENITY_NAME_ATTRIBUTE), amenity));
//        }

//
//        if(outDate.compareTo(LocalDateTime.now()) != 0) {
//            Predicate checkoutDateBeforeStartDate = cb.greaterThan(prJoin.get("startDate"), outDate);
//            Predicate checkInDateAfterEndDate = cb.lessThan(prJoin.get("endDate"), inDate);
//            Predicate d = cb.or(checkoutDateBeforeStartDate, checkInDateAfterEndDate);
//            conditions.add(d);
//        }

        System.out.println("page size " + pageSize);
        TypedQuery<Property> typedQuery = manager.createQuery(cq.select(rootProperty).distinct(true).where(cb.and(conditions.toArray(new Predicate[]{}))).orderBy(cb.asc(rootProperty.get(sortBy))));
        List<Property> list = typedQuery.setFirstResult(pageNo * pageSize).setMaxResults(pageSize).getResultList();
        System.out.println("lis " + list.size());
        if(amenityIds != null && !amenityIds.isEmpty()) {
            list = list.stream().filter(e -> arePropertyWithAmenities(e.getAmenities(), amenityIds)).collect(Collectors.toList());
        }

        if (outDate.toLocalDate().compareTo(LocalDate.now()) != 0 && !list.isEmpty()) {
            list = list.stream().filter(e -> arePropertyReservable(e.getReservations(), inDate, outDate)).collect(Collectors.toList());
        }

        System.out.println("LIST SIZE  " + list.size());
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.DATA_KEY, DtoUtil.toPropertyResponses(list));
        map.put(Constants.CURRENT_PAGE_KEY, pageNo);
        map.put(Constants.TOTAL_PAGE_KEY, list.size() % pageSize == 0 ? (list.size() / pageSize) : (list.size() / pageSize) + 1);
        map.put(Constants.SORT_BY_KEY, Constants.DEFAULT_PROPERTY_SORT_BY);
        return map;
    }

    private boolean arePropertyReservable(List<Reservation> reservations, LocalDateTime inDate, LocalDateTime outDate) {
        for (Reservation r : reservations) {
            if (inDate.compareTo(r.getStartDate()) >= 0 && inDate.compareTo(r.getEndDate()) <= 0
                    || outDate.compareTo(r.getStartDate()) >= 0 && outDate.compareTo(r.getEndDate()) <= 0
                    || inDate.compareTo(r.getStartDate()) <= 0 && outDate.compareTo(r.getEndDate()) >= 0
            ) {
                return false;
            }
        }
        return true;
    }

    private boolean arePropertyWithAmenities(List<Amenity> amenities, List<Integer> amenityIds) {
        if(amenityIds.size() > amenities.size()) {
            return false;
        }
        List<Integer> idList = getAmenitiesIdFromAmenities(amenities);
        return idList.containsAll(amenityIds);

    }

    private List<Integer> getAmenitiesIdFromAmenities(List<Amenity> amenities) {
        return amenities.stream().map(Amenity::getId).collect(Collectors.toList());
    }


    /**
     *
     * @param checkInDate >= current date
     * @param checkoutDate > checkInDate
     * @throws InvalidDateRangeException if the checkInDate is after checkoutDate
     */
    public void handleDateException(LocalDateTime checkInDate, LocalDateTime checkoutDate) {
        if(checkInDate.toLocalDate().isBefore(LocalDate.now())
                || checkoutDate.toLocalDate().isBefore(LocalDate.now())
                || checkInDate.toLocalDate().compareTo(checkoutDate.toLocalDate()) > 0
        ) {
            throw new InvalidDateRangeException(Constants.INVALID_DATE_RANGE);
        }

    }

    @Transactional
    public void uploadImage(String fileName, int propId) {
        manager.persist(PropertyImage.builder().name(fileName).property(Property.builder().id(propId).build()).build());
    }




}

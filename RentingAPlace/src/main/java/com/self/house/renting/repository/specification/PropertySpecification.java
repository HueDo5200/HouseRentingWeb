package com.self.house.renting.repository.specification;
import com.self.house.renting.constants.Constants;
import com.self.house.renting.model.entity.Property;
import com.self.house.renting.model.entity.Reservation;
import com.self.house.renting.model.entity.Users;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PropertySpecification {
    public static Specification<Property> hasAttributeLike(String keyword) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(keyword != null && !keyword.isEmpty()) {
                String key = "%" +keyword + "%";
                predicates.add(cb.like(root.get(Constants.PROPERTY_NAME_ATTRIBUTE), key));
                predicates.add(cb.like(root.get(Constants.PROPERTY_DESCRIPTION_ATTRIBUTE), key));
                predicates.add(cb.like(root.get(Constants.PROPERTY_PRICE_ATTRIBUTE).as(String.class), key));
                predicates.add(cb.equal(root.get(Constants.PROPERTY_BATHROOM_ATTRIBUTE).as(String.class), key));
                predicates.add(cb.equal(root.get(Constants.PROPERTY_BEDROOM_ATTRIBUTE).as(String.class), key));
                predicates.add(cb.equal(root.get(Constants.PROPERTY_KITCHEN_ATTRIBUTE).as(String.class), key));
            }
            return cb.or(predicates.toArray(new Predicate[]{}));
        });
    }

    public static Specification<Property> hasUnReservedProperty(int customerId) {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            Subquery<Reservation> subQuery = query.subquery(Reservation.class);
            Root<Reservation> reservationRoot = subQuery.from(Reservation.class);
            Predicate greaterThan = criteriaBuilder.greaterThanOrEqualTo(reservationRoot.get(Constants.RESERVATION_END_DATE_ATTRIBUTE), LocalDateTime.now());
            Predicate ofUser = criteriaBuilder.equal(reservationRoot.get(Constants.RESERVATION_USER_ATTRIBUTE), Users.builder().id(customerId).build());
             Predicate combinedQuery = criteriaBuilder.and(greaterThan, ofUser);
            subQuery.select(reservationRoot.get(Constants.RESERVATION_PROPERTY_ATTRIBUTE));
            subQuery.where(combinedQuery);
           return criteriaBuilder.not(root.get(Constants.PROPERTY_ID_ATTRIBUTE).in(subQuery));
        });
    }

    public static Specification<Property> hasReservedPropertyOfCustomer(int userId) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Property, Reservation> propertyReservationJoin = root.join(Constants.PROPERTY_RESERVATIONS_ATTRIBUTE);
            predicates.add(cb.greaterThan(propertyReservationJoin.get(Constants.RESERVATION_END_DATE_ATTRIBUTE), LocalDateTime.now()));
            predicates.add(cb.equal(propertyReservationJoin.get(Constants.RESERVATION_USER_ATTRIBUTE), Users.builder().id(userId).build()));
            return cb.and(predicates.toArray(new Predicate[] {}));
        });
    }

    public static Specification<Property> hasPropertyOfOwner(int userId) {
        return ((root, query, cb) ->
           cb.equal(root.get(Constants.PROPERTY_OWNER_ATTRIBUTE), Users.builder().id(userId).build())
        );
    }


}

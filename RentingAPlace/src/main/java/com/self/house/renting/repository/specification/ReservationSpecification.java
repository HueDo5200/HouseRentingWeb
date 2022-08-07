package com.self.house.renting.repository.specification;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.model.entity.Property;
import com.self.house.renting.model.entity.Reservation;
import com.self.house.renting.model.entity.Users;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReservationSpecification {
    public static Specification<Reservation> hasCurrentReservation(int ownerId) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Reservation, Property> reservationPropertyJoin = root.join(Constants.RESERVATION_PROPERTY_ATTRIBUTE);
            predicates.add(cb.equal(reservationPropertyJoin.get(Constants.PROPERTY_OWNER_ATTRIBUTE), Users.builder().id(ownerId).build()));
            predicates.add(cb.greaterThanOrEqualTo(root.get(Constants.RESERVATION_END_DATE_ATTRIBUTE), LocalDate.now().atTime(0, 0, 0)));
            Predicate reservedPredicate = cb.equal(root.get(Constants.RESERVATION_STATUS_ATTRIBUTE), Constants.RESERVED_STATUS);
            Predicate waitingPredicate = cb.equal(root.get(Constants.RESERVATION_STATUS_ATTRIBUTE), Constants.WAITING_RESERVATION_STATUS);
            Predicate eitherReservedOrWaiting = cb.or(reservedPredicate, waitingPredicate);
            predicates.add(eitherReservedOrWaiting);
            return cb.and(predicates.toArray(new Predicate[]{}));
        });
    }

    public static Specification<Reservation> hasReservationOfCustomer(int customerId) {
        return ((root, query, criteriaBuilder) ->
         criteriaBuilder.equal(root.get(Constants.RESERVATION_USER_ATTRIBUTE), Users.builder().id(customerId).build())
        );
    }

    public static Specification<Reservation> hasReservationSaleOfOwner(int ownerId) {
        return ((root, query, builder) -> {
           List<Predicate> predicates = new ArrayList<>();
            Join<Reservation, Property> reservationPropertyJoin = root.join(Constants.RESERVATION_PROPERTY_ATTRIBUTE);
            predicates.add(builder.equal(reservationPropertyJoin.get(Constants.PROPERTY_OWNER_ATTRIBUTE), Users.builder().id(ownerId).build()));
            predicates.add(builder.equal(root.get(Constants.RESERVATION_STATUS_ATTRIBUTE), Constants.COMPLETED_RESERVATION_STATUS));
            return builder.and(predicates.toArray(new Predicate[]{}));
        });
    }
}

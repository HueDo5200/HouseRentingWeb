package com.self.house.renting.repository.custom;

import com.self.house.renting.model.entity.Reservation;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ReservationCustomRepository {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public void updateReservationStatus(int status, int reservationId) {
        Reservation reservation = manager.find(Reservation.class, reservationId);
        reservation.setStatus(status);
        manager.merge(reservation);
    }
}

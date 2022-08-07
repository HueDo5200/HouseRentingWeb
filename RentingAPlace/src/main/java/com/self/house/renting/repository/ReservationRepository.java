package com.self.house.renting.repository;

import com.self.house.renting.model.entity.Property;
import com.self.house.renting.model.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends PagingAndSortingRepository<Reservation, Integer>, JpaSpecificationExecutor<Reservation>{

    List<Reservation> findByProperty(Property property);

  Page<Reservation> findAll(Specification<Reservation> specification, Pageable pageable);
}

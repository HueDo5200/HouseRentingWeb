package com.self.house.renting.repository;

import com.self.house.renting.model.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends PagingAndSortingRepository<Property, Integer>, JpaSpecificationExecutor<Property> {

    Page<Property> findAll(Specification<Property> specification, Pageable pageable);

    Boolean existsByName(String name);


}

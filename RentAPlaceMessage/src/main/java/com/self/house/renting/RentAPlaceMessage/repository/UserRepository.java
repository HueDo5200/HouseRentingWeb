package com.self.house.renting.RentAPlaceMessage.repository;

import com.self.house.renting.RentAPlaceMessage.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
}

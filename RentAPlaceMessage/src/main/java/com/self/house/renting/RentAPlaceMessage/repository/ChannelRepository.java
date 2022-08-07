package com.self.house.renting.RentAPlaceMessage.repository;

import com.self.house.renting.RentAPlaceMessage.model.entity.ChatChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends PagingAndSortingRepository<ChatChannel, Integer>, JpaSpecificationExecutor<ChatChannel> {

    Page<ChatChannel> findAll(Specification<ChatChannel> specification, Pageable pageable);






}

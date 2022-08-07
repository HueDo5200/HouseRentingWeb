package com.self.house.renting.RentAPlaceMessage.repository.specification;

import com.self.house.renting.RentAPlaceMessage.model.entity.ChatChannel;
import org.springframework.data.jpa.domain.Specification;


public class ChannelSpecification {
    public static Specification<ChatChannel> hasChannel(String userIdField, int customerId) {
       return ((root, query, criteriaBuilder) -> {
           query.distinct(true);
          return criteriaBuilder.equal(root.get("customerId"), customerId);
       });
    }
}

package com.self.house.renting.RentAPlaceMessage.repository.custom;

import com.self.house.renting.RentAPlaceMessage.model.entity.ChatChannel;
import com.self.house.renting.RentAPlaceMessage.model.entity.Message;
import com.self.house.renting.RentAPlaceMessage.model.entity.Users;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChannelCustomRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<ChatChannel> findAllChatChannelOfUser(int customerId, String fieldName) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ChatChannel> query = builder.createQuery(ChatChannel.class);
        Root<ChatChannel> channelRoot = query.from(ChatChannel.class);
        TypedQuery<ChatChannel> typedQuery = manager.createQuery(query.select(channelRoot).distinct(true).where(builder.equal(channelRoot.get(fieldName), customerId)));
        return typedQuery.getResultList();
    }

    public ChatChannel findChatChannelByUserIdAndChannelName(int userId, String channelName) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ChatChannel> query = builder.createQuery(ChatChannel.class);
        Root<ChatChannel> channelRoot = query.from(ChatChannel.class);
        Join<ChatChannel, Message> channelMessageJoin = channelRoot.join("messages");
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(channelMessageJoin.get("user"), Users.builder().id(userId).build()));
        predicates.add(builder.equal(channelRoot.get("channelName"), channelName));
        TypedQuery<ChatChannel> typedQuery = manager.createQuery(query.select(channelRoot).distinct(true).where(builder.and(predicates.toArray(new Predicate[]{}))));
        return typedQuery.getSingleResult();
    }


}

package com.self.house.renting.RentAPlaceMessage.service;

import com.self.house.renting.RentAPlaceMessage.constants.Constants;
import com.self.house.renting.RentAPlaceMessage.exception.ResourceNotFoundException;
import com.self.house.renting.RentAPlaceMessage.model.dto.request.MessageRequest;
import com.self.house.renting.RentAPlaceMessage.model.dto.request.NewChannelRequest;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.ChatChannelResponse;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.MessageResponse;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.MessageUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ChatServiceTest {
    private List<MessageResponse> messages;

    private Logger logger = LoggerFactory.getLogger(ChatServiceTest.class);
    @Autowired
    private ChatService chatService;

    @BeforeAll
    public void setup() {
        messages = new ArrayList<>();
        messages.add(MessageResponse.builder().id(1).chatChannelId(1).content("Hi! I am Quan").datePosted(LocalDateTime.of(2022, 5, 4, 8, 0, 0)).build());
        messages.add(MessageResponse.builder().id(2).chatChannelId(1).content("What can I do for you, sir?").datePosted(LocalDateTime.of(2022, 5, 4, 8, 5, 0)).build());
        messages.add(MessageResponse.builder().id(3).chatChannelId(1).content("have a nice day").datePosted(LocalDateTime.of(2022, 5, 5, 8, 0, 0)).build());

    }

    @Transactional
    @Test
    public void shouldReturnChatChannelResponseWhenFindById() {
      ChatChannelResponse expected = ChatChannelResponse.builder()
                .id(1)
                .channelName("Nha Ben Run(U Lesa) - Big Pine House")
                .dateCreated(LocalDateTime.of(2022, 12, 12, 0, 0, 0))
                .messages(messages)
                .build();
      ChatChannelResponse actual = chatService.findById(1);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getChannelName(), actual.getChannelName());
        assertEquals(expected.getDateCreated(), actual.getDateCreated());
        assertEquals(expected.getMessages().size(), actual.getMessages().size());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenChatChannelNotExisted() {
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           chatService.findById(22);
        }, Constants.RESOURCE_NOT_FOUND);
        Assertions.assertEquals(Constants.RESOURCE_NOT_FOUND, ex.getMessage());
    }

    @Test
    public void shouldReturnChatChannelWhenFindingByUserIdAndChannelName() {
            assertNotNull(chatService.isChannelExist(1, "Nha Ben Run(U Lesa) - Big Pine House"));
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenFindingChannelWithNotExistedUserIdAndChannelName() {
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            chatService.isChannelExist(40, "");
        }, Constants.USER_NOT_EXISTED);
        Assertions.assertEquals(Constants.USER_NOT_EXISTED, ex.getMessage());
    }

//    @Test
//    public void shouldReturnNullWhenFindingNonExistedChannelByUserIdAndName() {
//        logger.info("mes " + chatService.isChannelExist(1, "fsfsf"));
//    }

    @Test
    @Transactional
    public void shouldReturnAllChannelsOfUser() {
       List<ChatChannelResponse> actual = chatService.getAllChannelOfUser(1, Constants.CUSTOMER_ROLE);
       List<ChatChannelResponse> expected = new ArrayList<>();

       List<MessageResponse> messages = new ArrayList<>();
       messages.add(MessageResponse.builder().id(1)
               .user(MessageUserResponse.builder().id(1).username("Quan").build())
               .chatChannelId(1).datePosted(LocalDateTime.of(2022,  5, 4, 8, 0, 0))
               .content("Hi! I am Quan").build());
        messages.add(MessageResponse.builder().id(2)
                .user(MessageUserResponse.builder().id(2).username("owner").build())
                .chatChannelId(1).datePosted(LocalDateTime.of(2022,  5, 4, 8, 5, 0))
                .content("What can I do for you, sir?").build());
        messages.add(MessageResponse.builder().id(3)
                .user(MessageUserResponse.builder().id(1).username("Quan").build())
                .chatChannelId(1).datePosted(LocalDateTime.of(2022,  5, 5, 0, 0, 0))
                .content("What can I do for you, sir?").build());
       ChatChannelResponse response1 = ChatChannelResponse.builder().id(1)
               .channelName("Nha Ben Run(U Lesa) - Big Pine House")
               .dateCreated(LocalDateTime.of(2022, 12, 12, 0, 0, 0))
               .messages(messages)
               .build();

        ChatChannelResponse response2 = ChatChannelResponse.builder().id(16)
                .channelName("New Studio, lift, Hoan Kiem, near old quarter #B01")
                .dateCreated(LocalDateTime.of(2022, 5, 5, 0, 0, 0))
                .messages(new ArrayList<>())
                .build();

       expected.add(response1);
       expected.add(response2);
       assertEquals(expected.size(), actual.size());
       assertEquals(expected.get(0).getId(), actual.get(0).getId());
       assertEquals(expected.get(0).getChannelName(), actual.get(0).getChannelName());
       assertEquals(expected.get(0).getMessages().size() + 1, actual.get(0).getMessages().size());
        assertEquals(expected.get(1).getId(), actual.get(1).getId());
        assertEquals(expected.get(1).getChannelName(), actual.get(1).getChannelName());
        assertEquals(expected.get(1).getMessages().size(), actual.get(1).getMessages().size());
    }

    @Test
    public void shouldReturnMessageResponseWhenSendingMessage() {
        LocalDateTime now = LocalDateTime.now();
        MessageRequest request = MessageRequest.builder()
                .chatChannelId(1)
                .content("Test new message")
                .dateCreated(LocalDateTime.of(2022, 12, 12, 0, 0, 0))
                .datePosted(now)
                .userId(1)
                .username("Quan")
                .build();

        MessageResponse actual = chatService.sendMessage(request);
        MessageResponse expected = MessageResponse.builder()
                .id(4)
                .chatChannelId(1)
                .content("Test new message")
                .datePosted(now)
                .user(MessageUserResponse.builder().id(1).username("Quan").build())
                .build();
        assertEquals(expected.getChatChannelId(), actual.getChatChannelId());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getContent(), actual.getContent());
        assertEquals(expected.getDatePosted(), actual.getDatePosted());
        assertEquals(expected.getUser().getId(), actual.getUser().getId());
        assertEquals(expected.getUser().getUsername(), actual.getUser().getUsername());
    }

    @Test
    public void shouldReturnChatChannelResponseWhenCreatingNewChannel() {
        LocalDateTime now = LocalDateTime.now();
        NewChannelRequest request = NewChannelRequest.builder()
                .channelName("new channel")
                .dateCreated(now)
                .customerId(1)
                .ownerId(2)
                .build();
        ChatChannelResponse actual = chatService.initializeChannel(request);
        ChatChannelResponse expected = ChatChannelResponse.builder()
                .id(17)
                .channelName("new channel")
                .dateCreated(now)
                .messages(new ArrayList<>())
                .build();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getChannelName(), actual.getChannelName());
        assertEquals(expected.getDateCreated(), actual.getDateCreated());
assertEquals(expected.getMessages().size(), actual.getMessages().size());
    }


}

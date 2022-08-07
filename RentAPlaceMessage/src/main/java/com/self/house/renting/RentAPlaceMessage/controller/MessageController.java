package com.self.house.renting.RentAPlaceMessage.controller;

import com.self.house.renting.RentAPlaceMessage.constants.Constants;
import com.self.house.renting.RentAPlaceMessage.model.dto.request.MessageRequest;
import com.self.house.renting.RentAPlaceMessage.service.ChatService;
import com.self.house.renting.RentAPlaceMessage.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class MessageController {

    private ChatService chatService;

    @Autowired
    public MessageController(ChatService chatService) {
        this.chatService = chatService;
    }

    private Logger logger = LoggerFactory.getLogger(ChatService.class);

    @MessageMapping("/message")
    @SendTo("/chat/channel")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<Object> chat(@Valid @RequestBody MessageRequest messageDto) throws InterruptedException {
        Thread.sleep(1000);
        return ResponseUtil.customizeResponse(chatService.sendMessage(messageDto), HttpStatus.OK,  Constants.SEND_MESSAGE);
    }
}

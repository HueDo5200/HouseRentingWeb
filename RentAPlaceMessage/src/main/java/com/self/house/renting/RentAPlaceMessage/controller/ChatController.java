package com.self.house.renting.RentAPlaceMessage.controller;

import com.self.house.renting.RentAPlaceMessage.constants.Constants;
import com.self.house.renting.RentAPlaceMessage.model.dto.request.MessageRequest;
import com.self.house.renting.RentAPlaceMessage.model.dto.request.NewChannelRequest;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.ChatChannelResponse;
import com.self.house.renting.RentAPlaceMessage.service.ChatService;
import com.self.house.renting.RentAPlaceMessage.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.*;

@Validated
@RestController
@RequestMapping(Constants.CHAT_API)
public class ChatController {

   private ChatService chatService;
    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserChatInfo(@PathVariable @Min(1) int id) {
        List<ChatChannelResponse> responses = chatService.getAllChannelOfUser(id, Constants.CUSTOMER_ROLE);
        if(responses == null || responses.isEmpty()) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeResponse(responses, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }

    @GetMapping(value = "/owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOwnerChatInfo(@PathVariable @Min(1) int id) {
       List<ChatChannelResponse> responses = chatService.getAllChannelOfUser(id, Constants.OWNER_ROLE);
        if(responses == null || responses.isEmpty()) {
            return ResponseUtil.customizeResponse(null, HttpStatus.OK, Constants.EMPTY_LIST);
        }
        return ResponseUtil.customizeResponse(responses, HttpStatus.OK, Constants.GET_LIST_SUCCESS);
    }

    @GetMapping(value = "/channel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findChannelById(@PathVariable @Min(1) int id) {
        return ResponseUtil.customizeResponse(chatService.findById(id), HttpStatus.OK, Constants.RESOURCE_FOUND);
    }

    @PostMapping(value = "/message")
    public ResponseEntity<Object> sendMessage(@Valid @RequestBody MessageRequest messageDto) {
        return ResponseUtil.customizeResponse(chatService.sendMessage(messageDto), HttpStatus.OK,  Constants.SEND_MESSAGE);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createChannel(@Valid @RequestBody NewChannelRequest chatChannel) {
        if(chatService.isChannelExist(chatChannel.getCustomerId(), chatChannel.getChannelName()) != null) {
            return ResponseUtil.customizeResponse(null, HttpStatus.BAD_REQUEST, Constants.RESOURCE_ALREADY_EXISTED);
        }
        return ResponseUtil.customizeResponse(chatService.initializeChannel(chatChannel), HttpStatus.OK, Constants.NEW_RESOURCE);
    }





}

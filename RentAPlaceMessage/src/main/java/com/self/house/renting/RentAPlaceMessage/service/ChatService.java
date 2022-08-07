package com.self.house.renting.RentAPlaceMessage.service;

import com.self.house.renting.RentAPlaceMessage.model.dto.response.ChatChannelResponse;
import com.self.house.renting.RentAPlaceMessage.model.dto.request.MessageRequest;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.MessageResponse;
import com.self.house.renting.RentAPlaceMessage.model.dto.request.NewChannelRequest;
import com.self.house.renting.RentAPlaceMessage.model.entity.ChatChannel;

import java.util.List;

public interface ChatService {
    MessageResponse sendMessage(MessageRequest messageDto);
    ChatChannelResponse initializeChannel(NewChannelRequest newChannel);
    ChatChannel isChannelExist(int userId, String channelName);
   ChatChannelResponse findById(int id);
    List<ChatChannelResponse> getAllChannelOfUser(int userId, String role);

}

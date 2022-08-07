package com.self.house.renting.RentAPlaceMessage.serviceimpl;

import com.self.house.renting.RentAPlaceMessage.constants.Constants;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.ChatChannelResponse;
import com.self.house.renting.RentAPlaceMessage.model.dto.request.MessageRequest;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.MessageResponse;
import com.self.house.renting.RentAPlaceMessage.model.dto.request.NewChannelRequest;
import com.self.house.renting.RentAPlaceMessage.model.entity.ChatChannel;
import com.self.house.renting.RentAPlaceMessage.model.entity.Message;
import com.self.house.renting.RentAPlaceMessage.exception.ResourceNotFoundException;
import com.self.house.renting.RentAPlaceMessage.repository.ChannelRepository;
import com.self.house.renting.RentAPlaceMessage.repository.MessageRepository;
import com.self.house.renting.RentAPlaceMessage.repository.UserRepository;
import com.self.house.renting.RentAPlaceMessage.repository.custom.ChannelCustomRepository;
import com.self.house.renting.RentAPlaceMessage.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);
    private ChannelRepository channelRepository;


    private MessageRepository messageRepository;

    private UserRepository userRepository;

    private ChannelCustomRepository customRepository;


    @Autowired
    public ChatServiceImpl(ChannelRepository channelRepository, MessageRepository messageRepository, UserRepository userRepository, ChannelCustomRepository channelCustomRepository) {
        this.channelRepository = channelRepository;
        this.messageRepository = messageRepository;
        this.userRepository  = userRepository;
        this.customRepository = channelCustomRepository;
    }

    @Override
    public MessageResponse sendMessage(MessageRequest messageDto) {
        Message message = messageDto.getFromMessageDto();
        Message mess = messageRepository.save(message);
        logger.info("mesage " + mess);
        return mess.getMessageResponseFromMessage();
    }

    @Override
    public ChatChannelResponse initializeChannel(NewChannelRequest newChannel) {
        ChatChannel channel = newChannel.getChannelInfoFromNewChannel();
        ChatChannel result = channelRepository.save(channel);
        return result.getChatChannelResponse();
    }

    @Override
    public ChatChannel isChannelExist(int userId, String channelName) {
        throwUserNotFoundException(userId);
        return customRepository.findChatChannelByUserIdAndChannelName(userId, channelName);
    }

    @Transactional
    @Override
    public ChatChannelResponse findById(int id) {
        ChatChannel channel = handleChatChannelNotFoundException(id);
        return channel.getChatChannelResponse();
    }

    @Transactional
    @Override
    public List<ChatChannelResponse> getAllChannelOfUser(int userId, String role) {
        throwUserNotFoundException(userId);
        List<ChatChannel> channels;
        if (role.equalsIgnoreCase(Constants.CUSTOMER_ROLE)) {
            channels =  customRepository.findAllChatChannelOfUser(userId, Constants.CHANNEL_CUSTOMER_FIELD_ID);
        } else {
            channels = customRepository.findAllChatChannelOfUser(userId, Constants.CHANNEL_OWNER_FIELD_ID);
        }
        return getChatChannelResponses(channels);
    }

    public void throwUserNotFoundException(int userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_EXISTED));
    }

    public ChatChannel handleChatChannelNotFoundException(int channelId) {
        return channelRepository.findById(channelId).orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));
    }
    public List<ChatChannelResponse> getChatChannelResponses(List<ChatChannel> channels) {
        return channels.stream().map(ChatChannel::getChatChannelResponse).collect(Collectors.toList());
    }

}

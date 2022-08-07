package com.self.house.renting.RentAPlaceMessage.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.ChatChannelResponse;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.MessageResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="chat_channel")
@Entity
public class ChatChannel {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
   private LocalDateTime dateCreated;
    private String channelName;
    private int customerId;
    private int ownerId;


  @JsonManagedReference
    @OneToMany(mappedBy = "channel")
    private List<Message> messages;

    public ChatChannelResponse getChatChannelResponse() {
        return ChatChannelResponse
                .builder()
                .id(getId())
                .channelName(getChannelName())
               .dateCreated(getDateCreated())
                .messages(toMessageResponseList())
                .build();
    }

    public List<MessageResponse> toMessageResponseList() {
        if(this.getMessages() != null) {
            return this.getMessages().stream().map(Message::getMessageResponseFromMessage).collect(Collectors.toList());
        }
        return new ArrayList<>();

    }
}

package com.self.house.renting.RentAPlaceMessage.model.dto.request;

import com.self.house.renting.RentAPlaceMessage.model.entity.ChatChannel;
import com.self.house.renting.RentAPlaceMessage.model.entity.Message;
import com.self.house.renting.RentAPlaceMessage.model.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest {

    private int id;
    @Min(1)
    private int chatChannelId;
    private LocalDateTime dateCreated;
    @NotBlank
    private String username;
    @Min(1)
    private int userId;
    @NotBlank
    private String content;
    private LocalDateTime datePosted;

    @Override
    public int hashCode() {
        return Objects.hashCode(chatChannelId);
    }

    public Message getFromMessageDto() {
        return Message.builder().channel(ChatChannel.builder().id(getChatChannelId())
                        .dateCreated(getDateCreated()).build())
                .user(Users.builder().id(getUserId())
                        .username(getUsername())
                        .build())
                .content(getContent())
                .datePosted(getDatePosted()).build();
    }


}

package com.self.house.renting.RentAPlaceMessage.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatChannelResponse {

    @Min(1)
    private int id;
    private LocalDateTime dateCreated;
    @NotBlank
    private String channelName;
    private MessageUserResponse customer;
    private MessageUserResponse owner;
    private List<MessageResponse> messages;
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }



}

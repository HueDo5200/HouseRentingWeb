package com.self.house.renting.RentAPlaceMessage.model.dto.request;

import com.self.house.renting.RentAPlaceMessage.model.entity.ChatChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewChannelRequest {
    @NotNull
    private String channelName;
    private LocalDateTime dateCreated;
    @Min(1)
    private int customerId;
    @Min(1)
    private int ownerId;

    public ChatChannel getChannelInfoFromNewChannel() {
        return ChatChannel.builder().channelName(getChannelName()).dateCreated(getDateCreated()).customerId(getCustomerId()).ownerId(getOwnerId()).build();
    }

}

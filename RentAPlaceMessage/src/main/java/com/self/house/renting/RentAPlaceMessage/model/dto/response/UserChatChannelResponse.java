package com.self.house.renting.RentAPlaceMessage.model.dto.response;

import com.self.house.renting.RentAPlaceMessage.model.entity.ChatChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChatChannelResponse {
        @NotNull
        private List<ChatChannel> channels;
}

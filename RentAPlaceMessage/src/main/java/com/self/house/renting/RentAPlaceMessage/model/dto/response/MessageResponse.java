package com.self.house.renting.RentAPlaceMessage.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    @Min(1)
    private int id;
    @Min(1)
    private int chatChannelId;
    @NotBlank
    private String content;
    private LocalDateTime datePosted;
    @NotNull
    private MessageUserResponse user;

}

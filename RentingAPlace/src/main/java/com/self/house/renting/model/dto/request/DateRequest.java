package com.self.house.renting.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateRequest {
    private LocalDateTime checkInDate;
    private LocalDateTime checkoutDate;
}

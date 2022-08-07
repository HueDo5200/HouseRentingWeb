package com.self.house.renting.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {
    @Min(1)
    private int id;
    @Min(1)
    private int customerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Min(1)
    private double total;
    @Min(-1)
    private int status;
    private String propertyName;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}

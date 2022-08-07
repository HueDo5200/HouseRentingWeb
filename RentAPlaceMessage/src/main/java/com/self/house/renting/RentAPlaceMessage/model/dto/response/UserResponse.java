package com.self.house.renting.RentAPlaceMessage.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    @Min(1)
    private int customerId;

    @Override
    public int hashCode() {
        return Objects.hashCode(customerId);
    }
}

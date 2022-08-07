package com.self.house.renting.model.dto.request;

import com.self.house.renting.model.entity.Property;
import com.self.house.renting.model.entity.Reservation;
import com.self.house.renting.model.entity.Users;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReservationRequest {
    @Min(1)
    private int userId;
    @Min(1)
    private int propertyId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Min(1)
    private double total;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    /**
     *
     * @return reservation data converted from reservation request
     */
    public Reservation getReservationFromDto() {
        return Reservation.builder().users(Users.builder().id(getUserId()).build()).property(Property.builder().id(getPropertyId()).build()).startDate(getStartDate())
                .endDate(getEndDate()).total(getTotal()).build();
    }

}

package com.self.house.renting.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.self.house.renting.model.dto.response.ReservationResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="reservation")
public class Reservation {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private double total;

    private int status;

    @JsonBackReference(value = "users")
    @ManyToOne@JoinColumn(name="user_id")
    private Users users;

    @JsonBackReference(value = "property")
    @ManyToOne@JoinColumn(name="property_id")
    private Property property;

    /**
     *
     * @return  reservation response converted from reservation
     */
    public ReservationResponse getReservationResponseFromDto() {
        return ReservationResponse.builder().id(getId())
                .endDate(getEndDate())
                .customerId(getUsers().getId())
                .startDate(getStartDate())
                .status(getStatus())
                .total(getTotal())
                .propertyName(getProperty().getName())
                .build();
    }


}

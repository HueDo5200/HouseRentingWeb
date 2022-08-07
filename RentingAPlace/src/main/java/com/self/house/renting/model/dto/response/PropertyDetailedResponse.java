package com.self.house.renting.model.dto.response;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDetailedResponse {
    @Min(1)
    private int id;
    @NotBlank(message = "Property name should not be blank")
    private String name;
    private String description;
    @Min(0)
    private BigDecimal avgRating;
    @Min(0)
    private int ratingNum;
    @Min(1)
    private BigDecimal pricePerNight;
    @Min(1)
    private int bedroom;
    @Min(1)
    private int bathroom;
    @Min(1)
    private int kitchen;
    @Min(1)
    private int ownerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @NotBlank
    private String ownerName;
    @NotBlank
    private String ownerEmail;
    @NotNull
    private LocationResponse location;
    @NotNull
    private String typeName;
    @NotEmpty
    private List<AmenityResponse> amenities;
    private List<ImageResponse> propertyImages;
    @Min(1)
    private int reservationStatus;
    List<CurrentReservationResponse> currentReservations;
    @Min(1)
    private double total;
}

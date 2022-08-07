package com.self.house.renting.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagedPropertyResponse {
    private int id;

    @NotBlank(message = "Property name should not be blank")
    private String name;
    @NotBlank(message = "Description name should not be blank")
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
    @NotNull
    private LocationResponse location;
    @NotNull
    private TypeResponse propertyType;
    @NotNull
    private List<ImageResponse> propertyImages;

}

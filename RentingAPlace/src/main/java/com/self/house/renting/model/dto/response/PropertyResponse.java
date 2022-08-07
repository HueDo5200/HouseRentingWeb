package com.self.house.renting.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResponse {
    @Min(1)
    private int id;
    @NotBlank
    private String name;
    private List<ImageResponse> propertyImages;
    @Min(1)
    private int bedroom;
    @Min(1)
    private int bathroom;
    @Min(1)
    private int kitchen;
    @Min(1)
    private BigDecimal pricePerNight;
}

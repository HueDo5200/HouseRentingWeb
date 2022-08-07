package com.self.house.renting.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterRequest {
    @NotBlank(message = "City name for filtering should not be empty")
    private String city;
    @NotBlank(message = "Amenity's name for filtering should not be empty")
    private String amenityName;
    @NotBlank(message = "Type of property for filtering should not be empty")
    private String type;
}

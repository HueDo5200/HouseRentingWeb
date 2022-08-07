package com.self.house.renting.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationResponse {

    @Min(1)
    private int id;
    @NotBlank(message = "State is required")
    private String state;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Location details is required")
    private String details;

    private String description;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() != this.getClass()) {
            return false;
        }
        LocationResponse response = (LocationResponse) o;
        if(!Objects.equals(response.getCity(), this.getCity())) {
            return false;
        }
        if(!Objects.equals(response.getDescription(), this.getDescription())) {
            return false;
        }
        if(!Objects.equals(response.getDetails(), this.getDetails())) {
            return false;
        }
        if(!Objects.equals(response.getState(), this.getState())) {
            return false;
        }
        return Objects.equals(response.getId(), this.getId());
    }
}

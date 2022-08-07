package com.self.house.renting.model.dto.response;

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
public class AmenityResponse {
    @Min(1)
    private int id;
    private String name;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() != this.getClass()) {
            return false;
        }
        AmenityResponse response = (AmenityResponse) o;
        if(!Objects.equals(response.getId(), this.getId())) {
            return false;
        }
        return Objects.equals(response.getName(), this.getName());
    }
}

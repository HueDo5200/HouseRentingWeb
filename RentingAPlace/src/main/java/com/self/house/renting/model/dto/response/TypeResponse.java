package com.self.house.renting.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeResponse {
    @Min(1)
    private int id;
    @NotBlank(message = "Property type is required")
    private String name;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if(this.getClass() != o.getClass()) {
            return false;
        }
        TypeResponse response = (TypeResponse) o;
        if(!Objects.equals(response.getId(), this.getId())) {
            return false;
        }
        return Objects.equals(response.getName(), this.getName());
    }
}

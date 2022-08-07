package com.self.house.renting.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    @Min(1)
    private int id;
    @NotBlank(message = "Role's name is required")
    private String name;

    @Override
    public boolean equals(Object o) {
        if(o.getClass() != this.getClass()) {
            return false;
        }
        RoleResponse response = (RoleResponse) o;
        if(!Objects.equals(response.getId(), this.getId())) {
            return false;
        }
        return Objects.equals(response.getName(), this.getName());
     }
}

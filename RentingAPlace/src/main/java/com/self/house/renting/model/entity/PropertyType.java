package com.self.house.renting.model.entity;

import com.self.house.renting.model.dto.response.TypeResponse;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="property_type")
@Entity
public class PropertyType {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public TypeResponse convertPropertyTypeToDto() {
        return TypeResponse
                .builder()
                .id(getId())
                .name(getName())
                .build();
    }

    
}

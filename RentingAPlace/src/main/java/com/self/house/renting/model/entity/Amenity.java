package com.self.house.renting.model.entity;

import com.self.house.renting.model.dto.response.AmenityResponse;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="amenity")
@Entity
public class Amenity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public AmenityResponse convertAmenityToDto() {
        return AmenityResponse
                .builder()
                .id(getId())
                .name(getName())
                .build();
    }
    
}

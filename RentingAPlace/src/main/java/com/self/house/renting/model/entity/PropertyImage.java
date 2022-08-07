package com.self.house.renting.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.self.house.renting.model.dto.response.ImageResponse;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="property_image")
@Entity
public class PropertyImage {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

	@JsonBackReference(value = "images")
    @ManyToOne@JoinColumn(name="property_id")
    private Property property;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public ImageResponse toImageResponse() {
        return ImageResponse.builder().id(getId()).name(getName()).build();
    }

}

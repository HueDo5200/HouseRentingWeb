package com.self.house.renting.model.entity;

import com.self.house.renting.model.dto.response.LocationResponse;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="location")
@Entity
public class Location {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String state;
    private String city;
    private String details;
    @Column(name="description", columnDefinition = "varchar(255) default null")
    private String description;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public LocationResponse toLocationResponse() {
        return LocationResponse.builder().id(id).city(city)
                .state(state).description(description).details(details)
                .build();
    }
}

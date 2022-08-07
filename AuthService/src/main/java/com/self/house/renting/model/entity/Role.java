package com.self.house.renting.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="role")
public class Role {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    
}

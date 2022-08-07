package com.self.house.renting.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.self.house.renting.model.dto.response.RegisterResponse;
import com.self.house.renting.model.dto.response.RoleResponse;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Entity
public class Users {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name="phone_number")
    private String phone;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @ManyToOne@JoinColumn(name="role_id")
    private Role role;

    @JsonManagedReference(value = "users")
    @OneToMany(mappedBy = "users")
    private List<Reservation> reservations;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public RegisterResponse getRegisterResponseFromUser() {
        return RegisterResponse.builder().username(getUsername()).email(getEmail()).role(RoleResponse.builder().id(getRole().getId()).name(getRole().getName()).build()).build();
    }
}

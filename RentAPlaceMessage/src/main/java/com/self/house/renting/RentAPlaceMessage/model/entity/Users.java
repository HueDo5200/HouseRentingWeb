package com.self.house.renting.RentAPlaceMessage.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class Users {
    @Id
    private int id;
    private String username;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Message> messages;

}

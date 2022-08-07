package com.self.house.renting.RentAPlaceMessage.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.MessageResponse;
import com.self.house.renting.RentAPlaceMessage.model.dto.response.MessageUserResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="message")
public class Message {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @Column(name="date_posted")@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datePosted;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "chat_channel_id")
    private ChatChannel channel;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private Users user;


    public MessageResponse getMessageResponseFromMessage() {
        return MessageResponse.builder()
                .id(getId())
                .chatChannelId(getChannel().getId())
                .content(getContent())
                .datePosted(getDatePosted())
                .user(MessageUserResponse.builder().id(getUser().getId()).username(getUser().getUsername()).build())
                .build();

    }


}

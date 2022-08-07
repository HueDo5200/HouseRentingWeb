package com.self.house.renting.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String from;
    private String to;
    private String subject;
    private String content;
    private String contentType;
    private List<Object> attachments;


}

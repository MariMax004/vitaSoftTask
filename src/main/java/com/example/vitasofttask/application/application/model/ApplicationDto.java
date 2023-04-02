package com.example.vitasofttask.application.application.model;

import com.example.vitasofttask.application.user.model.UserDto;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ApplicationDto {
    private Long id;
    private String name;
    private ApplicationStatus status;

    private java.util.Date dateCreate;

    private String comment;

    private UserDto user;

}

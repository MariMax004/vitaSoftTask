package com.example.vitasofttask.application.user.model;


import com.example.vitasofttask.application.auth.domain.Token;
import com.example.vitasofttask.application.auth.model.TokenDto;
import com.example.vitasofttask.application.role.model.RoleDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;

    private String lastName;
    private java.util.Date birthdayDate;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    private List<RoleDto> roles;


}

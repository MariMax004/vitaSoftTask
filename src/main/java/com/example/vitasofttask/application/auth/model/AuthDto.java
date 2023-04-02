package com.example.vitasofttask.application.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthDto {
    @Schema(description="Логин для входа")
    private String login;
    @Schema(description="Пароль для входа")
    private String password;
}

package com.example.vitasofttask.application.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegistrationDto {
    @Schema(description="Email для регистрации")
    private String email;
    @Schema(description="Пароль для регистрации")
    private String password;
    @Schema(description="Имя")
    private String name;
    @Schema(description="Фамилия")
    private String lastName;
}


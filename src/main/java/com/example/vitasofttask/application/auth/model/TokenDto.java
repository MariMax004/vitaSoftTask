package com.example.vitasofttask.application.auth.model;

import lombok.Data;


@Data
public class TokenDto {
    private String authToken;
    private String accessToken;
}

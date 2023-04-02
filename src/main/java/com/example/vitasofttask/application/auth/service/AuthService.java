package com.example.vitasofttask.application.auth.service;


import com.example.vitasofttask.application.auth.model.AuthDto;
import com.example.vitasofttask.application.auth.model.RegistrationDto;
import com.example.vitasofttask.application.auth.model.TokenDto;

public interface AuthService {
    TokenDto authorization(AuthDto authDto);
    TokenDto updateToken(String accessToken);
    TokenDto registration(RegistrationDto registrationDto);
    void logout(TokenDto tokenDto);


}

package com.example.vitasofttask.application.auth.web;


import com.example.vitasofttask.application.auth.model.AuthDto;
import com.example.vitasofttask.application.auth.model.RegistrationDto;
import com.example.vitasofttask.application.auth.model.TokenDto;
import com.example.vitasofttask.application.auth.service.AuthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Api(tags="Авторизация")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenDto authorization(@RequestBody AuthDto authDto) throws IOException {
        log.info("login({})", authDto);
        return authService.authorization(authDto);
    }
    @PostMapping("/registration")
    public TokenDto registration(@RequestBody RegistrationDto registrationDto){
        log.info("reg({})", registrationDto);
        return authService.registration(registrationDto);
    }


    @PostMapping("/logout")
    public void logout(@RequestBody TokenDto tokenDto) {
        authService.logout(tokenDto);
    }

    @PutMapping("/new/token")
    public TokenDto updateToken(@RequestBody TokenDto tokenDto) {
        return authService.updateToken(tokenDto.getAccessToken());
    }


}

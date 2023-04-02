package com.example.vitasofttask.application.auth.service.impl;


import com.example.vitasofttask.application.auth.domain.Token;
import com.example.vitasofttask.application.auth.domain.TokenRepository;
import com.example.vitasofttask.application.auth.model.AuthDto;
import com.example.vitasofttask.application.auth.model.RegistrationDto;
import com.example.vitasofttask.application.auth.model.TokenDto;
import com.example.vitasofttask.application.auth.service.AuthService;
import com.example.vitasofttask.common.JwtObject;
import com.example.vitasofttask.application.role.domain.Role;
import com.example.vitasofttask.application.role.domain.RoleRepository;
import com.example.vitasofttask.application.role.model.RoleType;
import com.example.vitasofttask.application.user.domain.Customer;
import com.example.vitasofttask.application.user.domain.CustomerRepository;
import com.example.vitasofttask.errors.ErrorDescriptor;
import com.example.vitasofttask.utils.JwtUtils;
import com.example.vitasofttask.utils.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleRepository roleRepository;

    private final TokenRepository tokenRepository;
    private final CustomerRepository customerRepository;
    private final JwtUtils jwtUtils;

    @Override
    public TokenDto authorization(AuthDto authDto) {
        ErrorDescriptor.USER_AUTH_PROBLEM.throwIsFalse(customerRepository.existsByEmail(authDto.getLogin()));
        Customer user = customerRepository.getUserByEmail(authDto.getLogin());
        if (!ObjectUtils.isEmpty(user)) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            ErrorDescriptor.USER_AUTH_PROBLEM.throwIsFalse(passwordEncoder.matches(authDto.getPassword(),
                    user.getPassword()));
            Token token = new Token();
            token.setToken(jwtUtils.generateToken(TokenType.ACCESS, JwtObject.of(String.valueOf(user.getId()))));
            token.setUser(user);
            tokenRepository.save(token);
            TokenDto tokenDto = new TokenDto();
            tokenDto.setAccessToken(token.getToken());
            tokenDto.setAuthToken(jwtUtils.generateToken(TokenType.AUTH, JwtObject.of(String.valueOf(user.getId()))));
            return tokenDto;
        } else {
            throw ErrorDescriptor.USER_AUTH_PROBLEM.throwApplication();
        }
    }

    @Override
    public TokenDto updateToken(String accessToken) {
        ErrorDescriptor.TOKEN_ACCESS_NOT_FOUND.throwIsFalse(tokenRepository.existsByToken(accessToken));
        Token token = tokenRepository.getTokenByToken(accessToken);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(token.getToken());
        tokenDto.setAuthToken(jwtUtils.generateToken(TokenType.AUTH, JwtObject.of(String.valueOf(token.getUser().getId()))));
        return tokenDto;
    }

    @Override
    public TokenDto registration(RegistrationDto registrationDto) {
        ErrorDescriptor.USER_IS_CREATED.throwIsTrue(customerRepository.existsByEmail(registrationDto.getEmail()));
        Customer user = convertToUserRegistration(registrationDto);
        user = customerRepository.save(user);
        Token token = new Token();
        token.setToken(jwtUtils.generateToken(TokenType.ACCESS, JwtObject.of(String.valueOf(user.getId()))));
        token.setUser(user);
        tokenRepository.save(token);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(token.getToken());
        tokenDto.setAuthToken(jwtUtils.generateToken(TokenType.AUTH, JwtObject.of(String.valueOf(user.getId()))));
        log.info("id {}", String.valueOf(user.getId()));
        return tokenDto;
    }

    @Override
    public void logout(TokenDto tokenDto) {
        ErrorDescriptor.CUSTOMER_LOGOUT_ERROR.throwIsTrue(ObjectUtils.isEmpty(tokenRepository
                .getTokenByToken(tokenDto.getAccessToken())));
        tokenRepository.delete(tokenRepository.getTokenByToken(tokenDto.getAccessToken()));
    }

    public Customer convertToUserRegistration(RegistrationDto registrationDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Customer user = new Customer();
        user.setName(registrationDto.getName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode((registrationDto.getPassword())));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getRoleByName(RoleType.USER));
        user.setRoles(roles);
        return user;
    }



}

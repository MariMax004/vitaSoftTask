package com.example.vitasofttask.utils;

import com.example.vitasofttask.common.JwtObject;
import com.example.vitasofttask.configuration.JwtConfig;
import com.example.vitasofttask.errors.ErrorDescriptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;


    public String generateToken(TokenType type, JwtObject jwtObject) {
        try {
            Date now = new Date();
            Date exp = type.equals(TokenType.AUTH)
                    ? Date.from(LocalDateTime.now().plusHours(jwtConfig.getTimeOfActionAuthToken())
                    .atZone(ZoneId.systemDefault()).toInstant())
                    : Date.from(LocalDateTime.now().plusHours(jwtConfig.getTimeOfActionAccessToken())
                    .atZone(ZoneId.systemDefault()).toInstant());
            return Jwts.builder()
                    .setSubject(objectMapper.writeValueAsString(jwtObject))
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, jwtConfig.getJwtSecret())
                    .compact();
        } catch (JsonProcessingException e) {
            throw ErrorDescriptor.TOKEN_EXCEPTION.throwApplication();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getJwtSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public JwtObject getWordForToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtConfig.getJwtSecret()).parseClaimsJws(token).getBody();

            return objectMapper.readValue(claims.getSubject(), JwtObject.class);
        } catch (JsonProcessingException e) {
            throw ErrorDescriptor.TOKEN_EXCEPTION.throwApplication();
        }
    }

}

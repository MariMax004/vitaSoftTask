package com.example.vitasofttask.utils;

import com.example.vitasofttask.errors.model.ApplicationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class HttpResponseUtils extends ResponseEntityExceptionHandler {

    private static ObjectMapper staticObjectMapper;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public static void writeError(HttpServletResponse response, ApplicationError error, int status) {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(status);
        staticObjectMapper.writeValue(response.getOutputStream(), error);
    }

    @PostConstruct
    public void postConstruct() {
        staticObjectMapper = this.objectMapper;
    }

}

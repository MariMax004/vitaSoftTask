package com.example.vitasofttask.errors.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ApplicationError {
    private String error;
    private ErrorType errorType;
    private LocalDateTime time;
    private HttpStatus status;

    public ApplicationError(String message, ErrorType type, HttpStatus status) {
        this.error=message;
        this.errorType=type;
        this.status=status;
    }
}

package com.example.vitasofttask.errors.exception;

import com.example.vitasofttask.errors.model.ApplicationError;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(staticName = "of")
public class ApplicationException extends RuntimeException{
    private ApplicationError error;
}

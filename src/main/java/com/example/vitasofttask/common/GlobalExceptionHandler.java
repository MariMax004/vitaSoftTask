package com.example.vitasofttask.common;

import com.example.vitasofttask.errors.ErrorDescriptor;
import com.example.vitasofttask.errors.exception.ApplicationException;
import com.example.vitasofttask.errors.model.ApplicationError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApplicationError handleError404(NoHandlerFoundException ex) {
        return ErrorDescriptor.NOT_FOUND.applicationError();
    }


    @ResponseBody
    @ExceptionHandler(ApplicationException.class)
    public ApplicationError applicationException(ApplicationException ex, HttpServletResponse response) {
        response.setStatus(ex.getError().getStatus().value());
        return ex.getError();
    }



    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ApplicationError exception(IllegalArgumentException ex, HttpServletResponse response) {
        response.setStatus(ErrorDescriptor.TYPE_NOT_FOUND.getStatus().value());
        ApplicationError error = ErrorDescriptor.TYPE_NOT_FOUND.applicationError();
        error.setError(ex.getMessage());
        return error;
    }

}

package com.example.vitasofttask.errors;

import com.example.vitasofttask.errors.exception.ApplicationException;
import com.example.vitasofttask.errors.model.ApplicationError;
import com.example.vitasofttask.errors.model.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Getter
@AllArgsConstructor
public enum ErrorDescriptor {
    TYPE_NOT_FOUND("", ErrorType.APP, HttpStatus.BAD_REQUEST),
    NOT_FOUND("Запрошенный ресурс (интерфейс) не существует", ErrorType.APP, HttpStatus.NOT_FOUND),
    USER_IS_CREATED("Пользователь уже создан", ErrorType.DATA_BASE, HttpStatus.BAD_REQUEST),
    NO_PREVELEGIES("У вас нет прав совершать данное действие", ErrorType.DATA_BASE, HttpStatus.BAD_REQUEST),
    USER_AUTH_PROBLEM("Логин или пароль неверный", ErrorType.DATA_BASE, HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ACCESS("Неавторизованный доступ", ErrorType.APP, HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("Недостаточно прав для доступа к ресурсу", ErrorType.APP, HttpStatus.FORBIDDEN),
    TOKEN_EXCEPTION("Ошибка токена", ErrorType.APP, HttpStatus.BAD_REQUEST),
    TOKEN_ACCESS_NOT_FOUND("Токен доступа не найден", ErrorType.APP, HttpStatus.BAD_REQUEST),
    CUSTOMER_LOGOUT_ERROR("Пользователь уже вышел.", ErrorType.DATA_BASE, HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("Пользователь не найден", ErrorType.DATA_BASE, HttpStatus.BAD_REQUEST),
    NOT_YOUR_APP("Вы не являетесь владельцем заявки", ErrorType.DATA_BASE, HttpStatus.BAD_REQUEST),
    APPLICATION_NOT_FOUND("Заявка не найдена", ErrorType.DATA_BASE, HttpStatus.BAD_REQUEST);


    private final String message;
    private final ErrorType type;
    private final HttpStatus status;


    public void exception() {
        throw ApplicationException.of(applicationError());
    }

    public void throwIsTrue(Boolean flag) {
        if (flag) {
            exception();
        }
    }

    public void throwIsFalse(Boolean flag) {
        if (!flag) {
            exception();
        }
    }

    public void throwIsNull(Object object) {
        if (ObjectUtils.isEmpty(object)) {
            exception();
        }
    }

    public ApplicationError applicationError() {
        return new ApplicationError(this.message, this.type, this.status);
    }

    public ApplicationException throwApplication() {
        return ApplicationException.of(applicationError());
    }
}

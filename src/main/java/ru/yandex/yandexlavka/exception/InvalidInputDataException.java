package ru.yandex.yandexlavka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputDataException extends RuntimeException {
    public InvalidInputDataException() {
        super();
    }
}
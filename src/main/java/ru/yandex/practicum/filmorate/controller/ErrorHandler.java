package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.domain.exeptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.domain.exeptions.UserNotFoundException;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(final UserNotFoundException e) {
        return new ResponseEntity<>(Map.of("error:", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleFilmNotFoundException(final FilmNotFoundException e) {
        return new ResponseEntity<>(Map.of("error:", e.getMessage()), HttpStatus.NOT_FOUND);
    }

}

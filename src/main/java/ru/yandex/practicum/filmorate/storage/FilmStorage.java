package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

    Film create(Film film) throws ValidationException;

    Film update(Film film) throws ValidationException;

    int updateLastFilmId();

    void filmValidation(Film film) throws ValidationException;

}

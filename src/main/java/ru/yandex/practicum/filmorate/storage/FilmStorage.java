package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmDBService;

import java.util.List;

public interface FilmStorage {

    List<Film> getFilms(FilmDBService filmDBService);

    Film create(Film film) throws ValidationException;

    Film update(Film film) throws ValidationException;

}

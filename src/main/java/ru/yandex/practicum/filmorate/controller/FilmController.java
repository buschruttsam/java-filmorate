package ru.yandex.practicum.filmorate.controller;

import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;

@RestController
public class FilmController {

    private final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();

    @GetMapping("/films")
    public List<Film> findAll() {
        return filmStorage.getFilms();
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) throws ValidationException {
        return filmStorage.create(film);
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws ValidationException {
        return filmStorage.update(film);
    }


}

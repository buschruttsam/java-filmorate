package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;
import java.util.Optional;

@RestController
public class FilmController {

    private final InMemoryFilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService, InMemoryFilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        return filmStorage.getFilms();
    }

    @GetMapping("/films/{id}")
    public Film findById(@PathVariable int id) {
        return filmService.findById(id, filmStorage.getFilms());
    }

    @GetMapping(value = "films/popular")
    public List<Film> getFiltered0Films(@RequestParam(defaultValue = "10", required = false) int count) {
        return filmService.getFiltered0Films(count, filmStorage.getFilms(), filmStorage.getFilmsLikes());
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) throws ValidationException {
        return filmStorage.create(film);
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws ValidationException {
        return filmStorage.update(film);
    }

    @PutMapping(value = "films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId, filmStorage.getFilms(), filmStorage.getFilmsLikes());
    }

    @DeleteMapping(value = "films/{id}/like/{userId}")
    public Film removeLike(@RequestBody Film film, @PathVariable int id, @PathVariable int userId) {
        filmService.removeLike(id, userId, filmStorage.getFilmsLikes());
        return film;
    }


}

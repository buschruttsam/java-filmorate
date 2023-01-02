package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.GenreResponse;
import ru.yandex.practicum.filmorate.model.MpaResponse;
import ru.yandex.practicum.filmorate.service.FilmDBService;
import ru.yandex.practicum.filmorate.storage.FilmDBStorage;

import java.util.List;

@RestController
public class FilmController {

    private final FilmDBStorage filmDBStorage;
    private final FilmDBService filmDBService;

    public FilmController(FilmDBStorage filmDBStorage, FilmDBService filmDBService) {
        this.filmDBStorage = filmDBStorage;
        this.filmDBService = filmDBService;
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        return filmDBStorage.getFilms(filmDBService);
    }

    @GetMapping("/films/{id}")
    public Film findById(@PathVariable int id) throws ValidationException {
        return filmDBService.findById(id);
    }

    @GetMapping(value = "films/popular")
    public List<Film> getFiltered0Films(@RequestParam(defaultValue = "10", required = false) int count) {
        return filmDBService.getFiltered0Films(count);
    }

    @GetMapping("/films/genres")
    public List<String> getAllGenres() {
        return filmDBService.getAllGenres();
    }

    @GetMapping("/films/genres/{id}")
    public GenreResponse getGenresById(@PathVariable int id) {
        return filmDBService.getGenresById(id);
    }

    @GetMapping("/films/mpa")
    public List<String> getAllMpa() { return filmDBService.getAllMpa(); }

    @GetMapping("/films/mpa/{id}")
    public MpaResponse getMpaById(@PathVariable int id) { return filmDBService.getMpaById(id); }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) throws ValidationException {
        return filmDBStorage.create(film);
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws ValidationException {
        return filmDBStorage.update(film);
    }

    @PutMapping(value = "films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmDBService.addLike(id, userId);
    }

    @DeleteMapping(value = "films/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        filmDBService.removeLike(id, userId);
    }

}

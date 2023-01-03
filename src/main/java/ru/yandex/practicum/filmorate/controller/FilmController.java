package ru.yandex.practicum.filmorate.controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.FilmDBService;
import ru.yandex.practicum.filmorate.storage.FilmDBStorage;
import java.util.List;

@RestController
public class FilmController {

    private final FilmDBStorage filmDBStorage;
    private final FilmDBService filmDBService;
    // endpoints
    private final String ep_films = "/films";
    private final String ep_findFilmById = "/films/{id}";
    private final String ep_sortFilms = "/films/popular";
    private final String ep_findGenres = "/genres";
    private final String ep_findGenresById = "/genres/{id}";
    private final String ep_findMpa = "/mpa";
    private final String ep_findMpaById = "/mpa/{id}";
    private final String ep_filmLikes = "films/{id}/like/{userId}";

    public FilmController(FilmDBStorage filmDBStorage, FilmDBService filmDBService) {
        this.filmDBStorage = filmDBStorage;
        this.filmDBService = filmDBService;
    }

    @GetMapping(ep_films)
    public List<Film> findAll() {
        return filmDBStorage.getFilms(filmDBService);
    }

    @GetMapping(ep_findFilmById)
    public Film findById(@PathVariable int id) { return filmDBService.findById(id); }

    @GetMapping(value = ep_sortFilms)
    public List<Film> getFiltered0Films(@RequestParam(defaultValue = "10", required = false) int count) { return filmDBService.getFiltered0Films(count); }

    @GetMapping(ep_findGenres)
    public List<Genre> getAllGenres() {
        return filmDBService.getAllGenres();
    }

    @GetMapping(ep_findGenresById)
    public Genre getGenresById(@PathVariable int id) {
        return filmDBService.getGenresById(id);
    }

    @GetMapping(ep_findMpa)
    public List<Mpa> getAllMpa() { return filmDBService.getAllMpa(); }

    @GetMapping(ep_findMpaById)
    public Mpa getMpaById(@PathVariable int id) { return filmDBService.getMpaById(id); }

    @PostMapping(value = ep_films)
    public Film create(@RequestBody Film film) throws ValidationException { return filmDBStorage.create(film); }

    @PutMapping(value = ep_films)
    public Film update(@RequestBody Film film) throws ValidationException { return filmDBStorage.update(film); }

    @PutMapping(value = ep_filmLikes)
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmDBService.addLike(id, userId);
    }

    @DeleteMapping(value = ep_filmLikes)
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        filmDBService.removeLike(id, userId);
    }

}

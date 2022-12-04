package ru.yandex.practicum.filmorate.controller;

import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {
    private static final Logger post_log = LoggerFactory.getLogger(FilmController.class);

    private final List<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> findAll() {
        post_log.debug("Текущее количество фильмов: {}", films.size());
        return films;
    }

    @PostMapping(value = "/film")
    public Film create(@RequestBody Film film) {
        films.add(film);
        return film;
    }

    @PutMapping("/film")
    public Film update(@RequestBody Film film) {
        for (Film f : films){
            if (f.getId() == film.getId()){
                f.setName(film.getName());
                f.setDescription(film.getDescription());
                f.setDuration(film.getDuration());
                f.setReleaseDate(film.getReleaseDate());
            }
        }
        return film;
    }

}

package ru.yandex.practicum.filmorate.controller;

import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    public Film create(@RequestBody Film film) throws ValidationException {
        filmValidation (film);
        films.add(film);
        return film;
    }

    @PutMapping("/film")
    public Film update(@RequestBody Film film) throws ValidationException {
        filmValidation (film);
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

    private void filmValidation(Film film) throws ValidationException {
        if (film.getName().isEmpty() || film.getName().length() > 200) {
            throw new ValidationException("Film name is empty or length has over 200 symbols");
        }
        if (film.getDuration().toMinutes() <= 0) {
            throw new ValidationException("Film duration has zero or negative duration");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1995, 12, 28))){
            throw new ValidationException("Film release date is before Dec 28, 1995");
        }
    }

}

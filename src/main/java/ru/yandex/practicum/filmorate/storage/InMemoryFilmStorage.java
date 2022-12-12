package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Component
@Data
public class InMemoryFilmStorage implements FilmStorage {
    private int FILM_ID = 0;
    Set<Integer> filmIds = new HashSet<>();
    HashMap<Integer, Set<Integer>> filmsLikes = new HashMap<>();
    private List<Film> films = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public Film create(Film film) throws ValidationException {
        filmValidation(film);
        int filmId = updateLastFilmId();
        film.setId(filmId);
        films.add(film);
        filmIds.add(filmId);
        log.info("Film has been created, ID: {}", film.getId());
        return film;
    }

    public Film update(Film film) throws ValidationException {
        try {
            filmValidation(film);
        } catch (ValidationException e) {
            log.info(e.getMessage());
            return film;
        }
        for (Film f : films){
            if (f.getId() == film.getId()){
                f.setName(film.getName());
                f.setDescription(film.getDescription());
                f.setDuration(film.getDuration());
                f.setReleaseDate(film.getReleaseDate());
                log.info("Film has been updated, ID: {}", film.getId());
                return film;
            }
        }
        log.info("No film found, ID: {}", film.getId());
        throw new ValidationException("Film id not found");
    }

    // %%%%%%%%%% %%%%%%%%%% additional methods %%%%%%%%%% %%%%%%%%%%

    public int updateLastFilmId() {
        FILM_ID += 1;
        return FILM_ID;
    }

    public void filmValidation(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isEmpty() || film.getDescription().length() > 200) {
            throw new ValidationException("Film name is empty or length has over 200 symbols");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Film duration has zero or negative duration");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))){
            throw new ValidationException("Film release date is before Dec 28, 1995");
        }
    }
}

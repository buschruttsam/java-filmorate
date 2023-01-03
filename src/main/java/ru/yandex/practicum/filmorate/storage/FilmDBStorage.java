package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.domain.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmDBService;

@Data
@Repository
public class FilmDBStorage implements FilmStorage {

    private static final Logger log = LoggerFactory.getLogger(FilmDBStorage.class);
    private final JdbcTemplate jdbcTemplate;
    public FilmDBStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Film> getFilms(FilmDBService filmDBService) {
        List<Film> films = jdbcTemplate.query("SELECT * FROM films", (resultSet, rowNumber) -> filmDBService.getFilm(resultSet));
        for (Film film : films){
            film.setGenres(jdbcTemplate.query("SELECT * FROM film_genres WHERE film_id =?", (resultSet, rowNumber) -> filmDBService.getGenre(resultSet), film.getId()));


        }
        log.info("Current film amount is {}", films.size());
        return films;
    }

    public Film create(Film film) throws ValidationException {
        filmValidation(film);
        jdbcTemplate.update("INSERT INTO films (name, description, release_date, duration, mpa_rating_id) VALUES (?, ?, ?, ?, ?)", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        SqlRowSet filmSet = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE name = ? AND release_date = ?", film.getName(), film.getReleaseDate());
        if(filmSet.next()){
            film.setId(filmSet.getInt("id"));
            if (film.getGenres() != null){
                for (Genre genre : film.getGenres()){
                    jdbcTemplate.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)", film.getId(), genre.getId());
                }
            }
            log.info("Film has been created, ID: {}", film.getId());
        }
        return film;
    }

    public Film update(Film film) throws ValidationException {
        try {
            filmValidation(film);
        } catch (ValidationException e) {
            log.info(e.getMessage());
            return film;
        }
        SqlRowSet filmSet = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE id = ?", film.getId());
        if(filmSet.next()) {
            String sql = "MERGE INTO films (id, name, description, release_date, duration, mpa_rating_id) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
            jdbcTemplate.update("DELETE FROM film_genres WHERE film_id = ?", film.getId());
            if (film.getGenres() != null){
                List<Genre> newGenres = new ArrayList<>();
                for (Genre genre : film.getGenres()){
                    if (!newGenres.contains(genre)){
                        newGenres.add(genre);
                    }
                }
                for (Genre genre : newGenres){
                    jdbcTemplate.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)", film.getId(), genre.getId());
                }
                film.setGenres(newGenres);
            }
            log.info("Film has been updated, ID: {}", film.getId());
            return film;
        } else {
            throw new UserNotFoundException("Film not found");
        }
    }

    // %%%%%%%%%% %%%%%%%%%% additional methods %%%%%%%%%% %%%%%%%%%%

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

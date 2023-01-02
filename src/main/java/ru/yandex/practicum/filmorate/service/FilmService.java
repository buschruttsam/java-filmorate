package ru.yandex.practicum.filmorate.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.GenreResponse;
import ru.yandex.practicum.filmorate.model.MpaResponse;

import java.util.*;

public interface FilmService {

    Film findById(int id) throws ValidationException;

    List<Film> getFiltered0Films(int count);

    List<String> getAllGenres();

    GenreResponse getGenresById(int id);

    List<String> getAllMpa();

    MpaResponse getMpaById(int id);

    void addLike(int id, int userId);

    void removeLike(int id, int userId);

}

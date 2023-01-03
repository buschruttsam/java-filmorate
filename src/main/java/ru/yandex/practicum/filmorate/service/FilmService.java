package ru.yandex.practicum.filmorate.service;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.*;
import java.util.*;

public interface FilmService {

    Film findById(int id) throws ValidationException;

    List<Film> getFiltered0Films(int count);

    List<Genre> getAllGenres();

    Genre getGenresById(int id);

    List<Mpa> getAllMpa();

    Mpa getMpaById(int id);

    void addLike(int id, int userId);

    void removeLike(int id, int userId);

}

package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public interface FilmServiceInterface {

    Film findById (int postId, List<Film> films);

    void addLike (int filmId, int userId, List<Film> films, HashMap<Integer, Set<Integer>> filmsLikes);

    void removeLike (int filmId, int userId, HashMap<Integer, Set<Integer>> filmsLikes);

    List<Film> getFiltered0Films (int count, List<Film> films, HashMap<Integer, Set<Integer>> filmsLikes);

}

package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FilmService {

    public Optional<Film> findById (int postId, List<Film> films) {
        return films.stream()
                .filter(x -> x.getId() == postId)
                .findFirst();
    }

    public boolean addLike (int filmId, int userId, List<Film> films, HashMap<Integer, Set<Integer>> filmsLikes) {
        if (films.stream().anyMatch(x -> x.getId() == filmId)){
            filmsLikes.get(filmId).add(userId);
            return true;
        }
        return false;
    }

    public boolean removeLike (int filmId, int userId, HashMap<Integer, Set<Integer>> filmsLikes) {
        if (filmsLikes.containsKey(filmId)){
            filmsLikes.get(filmId).remove(userId);
        }
        return true;
    }

    public List<Film> getFiltered0Films (int count, List<Film> films, HashMap<Integer, Set<Integer>> filmsLikes) {
        return films.stream().sorted(Comparator.comparingInt(f0 -> filmsLikes.get(f0.getId()).size()))
                .limit(count)
                .collect(Collectors.toList());
    }

}
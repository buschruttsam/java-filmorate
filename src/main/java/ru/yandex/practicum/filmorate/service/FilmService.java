package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.domain.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Service
public class FilmService {

    public Film findById (int postId, List<Film> films) {
        Optional<Film> optFilm = films.stream().filter(x -> x.getId() == postId).findFirst();
        if (optFilm.isPresent()){
            return optFilm.get();
        } else {
            throw new UserNotFoundException("m:findById film not found");
        }
    }

    public void addLike (int filmId, int userId, List<Film> films, HashMap<Integer, Set<Integer>> filmsLikes) {
        System.out.println(filmId);
        if (films.stream().anyMatch(x -> x.getId() == filmId)){
            if (filmsLikes.containsKey(filmId)){
                filmsLikes.get(filmId).add(userId);
            } else {
                filmsLikes.put(filmId, new HashSet<>(userId));
            }
        } else {
            throw new UserNotFoundException("m:addLike film not found");
        }
    }

    public void removeLike (int filmId, int userId, HashMap<Integer, Set<Integer>> filmsLikes) {
        if (filmsLikes.containsKey(filmId)){
            if (filmsLikes.get(filmId).contains(userId)){
                filmsLikes.get(filmId).remove(userId);
            } else {
                throw new UserNotFoundException("m:removeLike user's like not found");
            }
        } else {
            throw new UserNotFoundException("m:removeLike film not found");
        }
    }

    public List<Film> getFiltered0Films (int count, List<Film> films, HashMap<Integer, Set<Integer>> filmsLikes) {
        return films.stream().sorted(Comparator.comparingInt(f0 -> - filmsLikes.get(f0.getId()).size()))
                .limit(count)
                .collect(Collectors.toList());
    }

}
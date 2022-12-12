package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Component
public class FilmService {

    public Optional<Film> findById(int postId, List<Film> films) {
        return films.stream()
                .filter(x -> x.getId() == postId)
                .findFirst();
    }

}

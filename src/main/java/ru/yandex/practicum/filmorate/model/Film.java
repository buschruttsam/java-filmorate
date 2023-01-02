package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Mpa mpa;
    private List<Genre> genres;
}

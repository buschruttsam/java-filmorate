package ru.yandex.practicum.filmorate.model;
import lombok.*;
import lombok.experimental.PackagePrivate;

import java.time.LocalDate;
import java.util.List;

@Data
@PackagePrivate
public class Film {
    int id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
    Mpa mpa;
    List<Genre> genres;
}

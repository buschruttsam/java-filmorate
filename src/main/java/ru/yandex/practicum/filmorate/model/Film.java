package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    final private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
}

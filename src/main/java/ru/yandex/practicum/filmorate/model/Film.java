package ru.yandex.practicum.filmorate.model;
import lombok.*;

@Data
public class Film {
    final private int id;
    private String name;
    private String description;
    private String releaseDate;
    private String duration;
}

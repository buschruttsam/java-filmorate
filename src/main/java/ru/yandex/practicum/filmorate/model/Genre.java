package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.experimental.PackagePrivate;

@Data
@PackagePrivate
public class Genre {
    Integer id;
    String name;
}

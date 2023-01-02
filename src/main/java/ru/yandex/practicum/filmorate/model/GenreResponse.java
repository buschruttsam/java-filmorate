package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.util.List;

@Data
public class GenreResponse {
    private int id;
    List<String> name;
}


        //"genres": [{ "id": 1}]

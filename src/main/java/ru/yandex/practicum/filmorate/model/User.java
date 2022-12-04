package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.time.LocalDate;

@Data
public class User {
    final private int id = 1;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}

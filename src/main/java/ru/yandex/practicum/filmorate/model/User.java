package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}

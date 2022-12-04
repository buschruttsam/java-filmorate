package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
public class User {
    final private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate date;
}

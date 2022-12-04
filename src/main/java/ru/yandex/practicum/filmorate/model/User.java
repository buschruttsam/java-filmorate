package ru.yandex.practicum.filmorate.model;
import lombok.*;

@Data
public class User {
    final private int id;
    private String email;
    private String login;
    private String name;
    private String date;
}

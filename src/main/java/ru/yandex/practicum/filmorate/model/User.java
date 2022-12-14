package ru.yandex.practicum.filmorate.model;
import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    private String login;
    private String name;
    @Email
    private String email;
    private LocalDate birthday;
}

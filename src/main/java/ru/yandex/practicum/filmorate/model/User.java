package ru.yandex.practicum.filmorate.model;
import lombok.*;
import lombok.experimental.PackagePrivate;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
@PackagePrivate
public class User {
    int id;
    String login;
    String name;
    @Email
    String email;
    LocalDate birthday;
}

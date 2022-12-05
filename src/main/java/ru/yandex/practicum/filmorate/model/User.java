package ru.yandex.practicum.filmorate.model;
import lombok.*;
import org.junit.Before;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NotBlank
    private String login;
    private String name;
    @Email
    private String email;
    @Past //?
    private LocalDate birthday;
}

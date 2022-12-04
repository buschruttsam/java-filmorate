package ru.yandex.practicum.filmorate.controller;

import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private static final Logger post_log = LoggerFactory.getLogger(UserController.class);

    private final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> findAll() {
        post_log.debug("Текущее количество фильмов: {}", users.size());
        return users;
    }

    @PostMapping(value = "/user")
    public User create(@RequestBody User user) throws ValidationException {
        userValidation(user);
        users.add(user);
        return user;
    }

    @PutMapping("/user")
    public User update(@RequestBody User user) throws ValidationException {
        userValidation(user);
        for (User u : users){
            if (u.getId() == user.getId()){
                u.setName(u.getName());
                u.setEmail(user.getEmail());
                u.setLogin(user.getLogin());
                u.setDate(user.getDate());
            }
        }
        return user;
    }

    private void userValidation(User user) throws ValidationException {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("User email is empty or has no @");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("User login is empty or has empty symbols");
        }
        if (user.getDate().isAfter(LocalDate.now())){
            throw new ValidationException("User birth date is in future");
        }

    }

}

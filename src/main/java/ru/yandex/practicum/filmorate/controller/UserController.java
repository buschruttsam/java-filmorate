package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;

@RestController
public class UserController {

    private final InMemoryUserStorage userStorage = new InMemoryUserStorage();

    @GetMapping("/users")
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) throws ValidationException {
        return userStorage.create(user);
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) throws ValidationException {
        return userStorage.update(user);
    }

}

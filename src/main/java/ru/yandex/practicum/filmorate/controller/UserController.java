package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserDBService;
import ru.yandex.practicum.filmorate.storage.UserDBStorage;

import java.util.List;

@RestController
public class UserController {
    private final UserDBStorage userDBStorage;
    private final UserDBService userDBService;

    public UserController(UserDBService userDBService, UserDBStorage userDBStorage) {
        this.userDBService = userDBService;
        this.userDBStorage = userDBStorage;
    }

    @GetMapping("/users")
    public List<User> findAll() { return userDBStorage.findAll(); }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable int id) {
        return userDBService.findById(id, userDBStorage.findAll());
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getAllFriends(@PathVariable int id) {
        return userDBService.getAllFriends(id, userDBStorage);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userDBService.getCommonFriends(id, otherId, userDBStorage);
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) throws ValidationException {
        return userDBStorage.create(user);
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) throws ValidationException {
        return userDBStorage.update(user);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userDBService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        userDBService.removeFriend(id, friendId);
    }

}

package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final InMemoryUserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(InMemoryUserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @GetMapping("/user/{id}")
    public Optional<User> findById(@PathVariable int id) {
        return userService.findById(id, userStorage.findAll());
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getAllFriends(@PathVariable int id) {
        return userService.getAllFriends(id, userStorage.getFriendPairs(), userStorage.getUsers());
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId, userStorage.getFriendPairs(), userStorage.getUsers());
    }

    @PostMapping("/users")
    public List<User> create(@RequestBody User user) {
        System.out.println("FLAG0-- ");
        return userStorage.findAll();
    }
    /*
    public User create(@RequestBody User user) throws ValidationException {
        System.out.println("FLAG0-- ");
        return userStorage.create(user);
    }
    */


    @PutMapping(value = "/users")
    public User update(@RequestBody User user) throws ValidationException {
        return userStorage.update(user);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId, userStorage.getFriendPairs(), userStorage.getUserIds());
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.removeFriend(id, friendId, userStorage.getFriendPairs());
    }

}

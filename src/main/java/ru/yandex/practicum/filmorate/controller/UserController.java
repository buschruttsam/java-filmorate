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
    // endpoints
    private final String ep_users = "/users";
    private final String ep_findUserById = "/users/{id}";
    private final String ep_findAllUserFriends = "/users/{id}/friends";
    private final String ep_findUserCommonFriends = "/users/{id}/friends/common/{otherId}";
    private final String ep_userFriend = "/users/{id}/friends/{friendId}";


    public UserController(UserDBService userDBService, UserDBStorage userDBStorage) {
        this.userDBService = userDBService;
        this.userDBStorage = userDBStorage;
    }

    @GetMapping(ep_users)
    public List<User> findAll() { return userDBStorage.findAll(userDBService); }

    @GetMapping(ep_findUserById)
    public User findById(@PathVariable int id) { return userDBService.findById(id); }

    @GetMapping(ep_findAllUserFriends)
    public List<User> getAllFriends(@PathVariable int id) {
        return userDBService.getAllFriends(id, userDBStorage);
    }

    @GetMapping(ep_findUserCommonFriends)
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) { return userDBService.getCommonFriends(id, otherId, userDBStorage); }

    @PostMapping(ep_users)
    public User create(@RequestBody User user) throws ValidationException { return userDBStorage.create(user); }

    @PutMapping(value = ep_users)
    public User update(@RequestBody User user) throws ValidationException { return userDBStorage.update(user); }

    @PutMapping(value = ep_userFriend)
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userDBService.addFriend(id, friendId);
    }

    @DeleteMapping(value = ep_userFriend)
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) { userDBService.removeFriend(id, friendId); }

}

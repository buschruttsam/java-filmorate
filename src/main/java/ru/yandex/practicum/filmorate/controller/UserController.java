package ru.yandex.practicum.filmorate.controller;

import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
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
    public User create(@RequestBody User user) {
        users.add(user);
        return user;
    }

    @PutMapping("/user")
    public User update(@RequestBody User user) {
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

}

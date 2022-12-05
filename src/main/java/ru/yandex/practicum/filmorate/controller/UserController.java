package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@Data
public class UserController {
    private static int USER_ID = 0;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("Current user amount is {}", users.size());
        return users;
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) throws ValidationException {
        if (user.getName() == null || Objects.equals(user.getName(), "")){user.setName(user.getLogin());}
        userValidation(user);
        user.setId(getLastUserId());
        users.add(user);
        log.info("User has been created, ID: {}", user.getId());
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) throws ValidationException {
        if (user.getName() == null || Objects.equals(user.getName(), "")){user.setName(user.getLogin());}
        userValidation(user);
        for (User u : users){
            if (u.getId() == user.getId()){
                u.setName(user.getName());
                u.setEmail(user.getEmail());
                u.setLogin(user.getLogin());
                u.setBirthday(user.getBirthday());
                log.info("User has been updated, ID: {}", u.getId());
                return user;
            }
        }
        log.info("No user found, ID: {}", user.getId());
        throw new ValidationException("User id not found");
    }

    // %%%%%%%%%% %%%%%%%%%% additional methods %%%%%%%%%% %%%%%%%%%%

    private void userValidation(User user) throws ValidationException {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("User email is empty or has no @");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("User login is empty or has empty symbols");
        }
        if (user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("User birth date is in future");
        }

    }

    private int getLastUserId() {
        USER_ID += 1;
        return USER_ID;
    }

}

package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Data
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("Current user amount is {}", users.size());
        return users;
    }

    @PostMapping(value = "/users")
    public boolean create(@RequestBody User user) {
        try {
            userValidation(user);
        } catch (ValidationException e) {
            log.info(e.getMessage());
            return false;
        }
        users.add(user);
        log.info("User has been created, ID: {}", user.getId());
        return true;
    }

    @PutMapping("/users")
    public boolean update(@RequestBody User user) {
        try {
            userValidation(user);
        } catch (ValidationException e) {
            log.info(e.getMessage());
            return false;
        }
        for (User u : users){
            if (u.getId() == user.getId()){
                u.setName(u.getName());
                u.setEmail(user.getEmail());
                u.setLogin(user.getLogin());
                u.setDate(user.getDate());
            }
        }
        log.info("User has been updated, ID: {}", user.getId());
        return true;
    }

    // %%%%%%%%%% %%%%%%%%%% additional methods %%%%%%%%%% %%%%%%%%%%

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

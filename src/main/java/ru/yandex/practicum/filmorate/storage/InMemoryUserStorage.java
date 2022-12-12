package ru.yandex.practicum.filmorate.storage;

import javafx.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private int USER_ID = 0;
    Set<Integer> userIds;
    Set<Pair<Integer, Integer>> friendPairs;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final List<User> users = new ArrayList<>();

    public Set<Pair<Integer, Integer>> getFriendPairs() {
        return friendPairs;
    }

    public List<User> getUsers() {
        return users;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public List<User> findAll() {
        log.info("Current user amount is {}", users.size());
        return users;
    }

    public User create(User user) throws ValidationException {
        System.out.println("FLAG-- ");
        if (user.getName() == null || Objects.equals(user.getName(), "")){user.setName(user.getLogin());}
        userValidation(user);
        int userId = getLastUserId();
        user.setId(userId);
        users.add(user);
        userIds.add(userId);
        //log.info("User has been created, ID: {}", user.getId());
        return user;
    }

    public User update(User user) throws ValidationException {
        if (user.getName() == null || Objects.equals(user.getName(), "")){user.setName(user.getLogin());}
        userValidation(user);
        for (User u : users){
            if (u.getId() == user.getId()){
                u.setName(user.getName());
                u.setEmail(user.getEmail());
                u.setLogin(user.getLogin());
                u.setBirthday(user.getBirthday());
                //log.info("User has been updated, ID: {}", u.getId());
                return user;
            }
        }
        //log.info("No user found, ID: {}", user.getId());
        throw new ValidationException("User id not found");
    }

    // %%%%%%%%%% %%%%%%%%%% additional methods %%%%%%%%%% %%%%%%%%%%

    public void userValidation(User user) throws ValidationException {
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("User login is empty or has empty symbols");
        }
        if (user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("User birth date is in future");
        }
    }

    public int getLastUserId() {
        USER_ID += 1;
        return USER_ID;
    }
}

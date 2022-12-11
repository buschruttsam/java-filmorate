package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> findAll();

    public User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;

    void userValidation(User user) throws ValidationException;

    int getLastUserId();
}

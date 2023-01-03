package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserDBService;

import java.util.List;

public interface UserStorage {

    List<User> findAll(UserDBService userDBService);

    User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;

}

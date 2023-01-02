package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDBStorage;

import java.util.*;

public interface UserService {

    User findById(int userId, List<User> users);

    void addFriend (int id, int friendId);

    void removeFriend (int id, int friendId);

    List<User> getAllFriends (int id, UserDBStorage userDBStorage);

    List<User> getCommonFriends (int id, int friendId, UserDBStorage userDBStorage);


}

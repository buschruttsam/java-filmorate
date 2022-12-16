package ru.yandex.practicum.filmorate.service;

import kotlin.Pair;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserServiceInterface {

    User findById(int userId, List<User> users);

    void addFriend (int id, int friendId, Set<Pair<Integer, Integer>> friendPairs, Set<Integer> userIds);

    void removeFriend (int id, int friendId, Set<Pair<Integer, Integer>> friendPairs);

    List<User> getAllFriends (int id, Set<Pair<Integer, Integer>> friendPairs, List<User> users);

    List<User> getCommonFriends (int id, int friendId, Set<Pair<Integer, Integer>> friendPairs, List<User> users);

}

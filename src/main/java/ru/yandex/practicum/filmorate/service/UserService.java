package ru.yandex.practicum.filmorate.service;

import javafx.util.Pair;
import org.springframework.cglib.beans.FixedKeySet;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    public Optional<User> findById(int userId, List<User> users) {
        return users.stream()
                .filter(x -> x.getId() == userId)
                .findFirst();
    }

    public boolean addFriend (int id, int friendId, Set<Pair<Integer, Integer>> friendPairs, Set<Integer> userIds) {
        if (userIds.contains(id) && userIds.contains(friendId)) {
            friendPairs.add(new Pair<>(id, friendId));
            friendPairs.add(new Pair<>(friendId, id));
            return true;
        } else {
            return false;
        }
    }

    public boolean removeFriend (int id, int friendId, Set<Pair<Integer, Integer>> friendPairs) {
        if (friendPairs.contains(new Pair<>(id, friendId))) {
            friendPairs.remove(new Pair<>(id, friendId));
            friendPairs.remove(new Pair<>(friendId, id));
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAllFriends (int id, Set<Pair<Integer, Integer>> friendPairs, List<User> users) {
        List<User> userFriends = new ArrayList<>();
        Set<Integer> friendIds = new HashSet<>();
        for (Pair<Integer, Integer> friendPair : friendPairs){
            if (friendPair.getKey() == id){
                friendIds.add(friendPair.getValue());
            }
        }
        for (int retainId : friendIds){
            userFriends.add(users.stream().filter(x -> x.getId() == retainId).findFirst().get());
        }
        return userFriends;
    }

    public List<User> getCommonFriends (int id, int friendId, Set<Pair<Integer, Integer>> friendPairs, List<User> users) {
        List<User> commonFriends = new ArrayList<>();
        Set<Integer> userFriendIds = new HashSet<>();
        Set<Integer> otherFriendIds = new HashSet<>();
        Set<Integer> commonFriendIds = new HashSet<>();
        for (Pair<Integer, Integer> friendPair : friendPairs){
            if (friendPair.getKey() == id){
                userFriendIds.add(friendPair.getValue());
            }
            if (friendPair.getKey() == friendId){
                otherFriendIds.add(friendPair.getValue());
            }
        }
        commonFriendIds = userFriendIds.stream().distinct().filter(otherFriendIds::contains).collect(Collectors.toSet());
        for (int retainId : commonFriendIds){
            commonFriends.add(users.stream().filter(x -> x.getId() == retainId).findFirst().get());
        }
        return commonFriends;
    }

}

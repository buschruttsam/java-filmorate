package ru.yandex.practicum.filmorate.service;

import kotlin.Pair;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.domain.exeptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.domain.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {


    public User findById(int userId, List<User> users) {
        Optional<User> optUser = users.stream().filter(x -> x.getId() == userId).findFirst();
        if (optUser.isPresent()){
            return optUser.get();
        } else {
            throw new FilmNotFoundException("m:findById user not found");
        }
    }

    public void addFriend (int id, int friendId, Set<Pair<Integer, Integer>> friendPairs, Set<Integer> userIds) {
        if (userIds.contains(id) && userIds.contains(friendId)) {
            friendPairs.add(new Pair<>(id, friendId));
            friendPairs.add(new Pair<>(friendId, id));
        } else {
            throw new UserNotFoundException("m:addFriend user or friend not found");
        }
    }

    public void removeFriend (int id, int friendId, Set<Pair<Integer, Integer>> friendPairs) {
        if (friendPairs.contains(new Pair<>(id, friendId))) {
            friendPairs.remove(new Pair<>(id, friendId));
            friendPairs.remove(new Pair<>(friendId, id));
        } else {
            throw new UserNotFoundException("m:removeFriend friend not found");
        }
    }

    public List<User> getAllFriends (int id, Set<Pair<Integer, Integer>> friendPairs, List<User> users) {
        List<User> userFriends = new ArrayList<>();
        Set<Integer> friendIds = new HashSet<>();
        for (Pair<Integer, Integer> friendPair : friendPairs){
            if (friendPair.getFirst() == id){
                friendIds.add(friendPair.getSecond());
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
        Set<Integer> commonFriendIds;
        for (Pair<Integer, Integer> friendPair : friendPairs){
            if (friendPair.getFirst() == id){
                userFriendIds.add(friendPair.getSecond());
            }
            if (friendPair.getFirst() == friendId){
                otherFriendIds.add(friendPair.getSecond());
            }
        }
        commonFriendIds = userFriendIds.stream().distinct().filter(otherFriendIds::contains).collect(Collectors.toSet());
        for (int retainId : commonFriendIds){
            commonFriends.add(users.stream().filter(x -> x.getId() == retainId).findFirst().get());
        }
        return commonFriends;
    }

}

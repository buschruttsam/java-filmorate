package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.domain.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDBStorage;

import java.util.*;

@Data
@Repository
public class UserDBService implements UserService {

    private final JdbcTemplate jdbcTemplate;

    public UserDBService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(int userId, List<User> users) {
        Optional<User> optUser = users.stream().filter(x -> x.getId() == userId).findFirst();
        if (optUser.isPresent()){
            return optUser.get();
        } else {
            throw new UserNotFoundException("m:findById user not found");
        }
    }

    public void addFriend (int id, int friendId) {
        if (checkIfUserIdExist(id) && checkIfUserIdExist(friendId)){
            SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_friends WHERE user_id = ? AND friend_id = ?",
                    id, friendId);
            if (!userSet.next()){
                jdbcTemplate.update("INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)",
                        id, friendId);
            }
        } else {
            throw new UserNotFoundException("m:addFriend user or friend not found");
        }
    }

    public void removeFriend (int id, int friendId) {
        SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_friends WHERE user_id = ? AND friend_id = ?",
                id, friendId);
        if (userSet.next()){
            jdbcTemplate.update("DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?",
                    id, friendId);
        }
    }

    public List<User> getAllFriends (int id, UserDBStorage userDBStorage) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id IN (SELECT friend_id FROM user_friends WHERE user_id = ?)",
                (resultSet, rowNumber) -> userDBStorage.getUser(resultSet), id);
    }

    public List<User> getCommonFriends (int id, int friendId, UserDBStorage userDBStorage) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id IN (SELECT friend_id FROM (SELECT * FROM user_friends WHERE user_id = ?) WHERE user_id =?)",
                (resultSet, rowNumber) -> userDBStorage.getUser(resultSet), id, friendId);
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%
    boolean checkIfUserIdExist (int id) {
        SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE id = ?", id);
        return userSet.next();
    }


}

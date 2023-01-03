package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.domain.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDBStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Data
@Repository
public class UserDBService implements UserService {

    private final JdbcTemplate jdbcTemplate;

    public UserDBService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(int userId) {
        SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE id = ?", userId);
        if (userSet.next()){
            return getUserFromRow (userSet);
        } else {
            throw new UserNotFoundException("m:findById user not found");
        }
    }

    public void addFriend (int id, int friendId) {
        if (checkIfUserIdExist(id) && checkIfUserIdExist(friendId)){
            SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_friends WHERE user_id = ? AND friend_id = ?", id, friendId);
            if (!userSet.next()){
                jdbcTemplate.update("INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)", id, friendId);
            }
        } else {
            throw new UserNotFoundException("m:addFriend user or friend not found");
        }
    }

    public void removeFriend (int id, int friendId) {
        SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_friends WHERE user_id = ? AND friend_id = ?", id, friendId);
        if (userSet.next()){
            jdbcTemplate.update("DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?", id, friendId);
        }
    }

    public List<User> getAllFriends (int id, UserDBStorage userDBStorage) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id IN (SELECT friend_id FROM user_friends WHERE user_id = ?)", (resultSet, rowNumber) -> getUser(resultSet), id);
    }

    public List<User> getCommonFriends (int id, int friendId, UserDBStorage userDBStorage) {
        String sql = "SELECT * FROM users WHERE id IN (SELECT friend_id FROM (SELECT friend_id FROM user_friends WHERE user_id =?) WHERE friend_id IN (SELECT friend_id FROM user_friends WHERE user_id = ?))";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> getUser(resultSet), id, friendId);
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%
    boolean checkIfUserIdExist (int id) {
        SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE id = ?", id);
        return userSet.next();
    }

    public User getUserFromRow (SqlRowSet userSet) {
        User user = new User();
        user.setId(userSet.getInt("id"));
        user.setLogin(userSet.getString("login"));
        user.setName(userSet.getString("name"));
        user.setEmail(userSet.getString("email"));
        user.setBirthday(Objects.requireNonNull(userSet.getDate("birthday")).toLocalDate());
        return user;
    }

    public User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setBirthday(resultSet.getDate("birthday").toLocalDate());
        return user;
    }

}

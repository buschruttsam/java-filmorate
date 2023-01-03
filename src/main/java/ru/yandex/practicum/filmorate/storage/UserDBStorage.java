package ru.yandex.practicum.filmorate.storage;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.domain.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserDBService;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@Repository
public class UserDBStorage implements UserStorage {

    private final Logger log = LoggerFactory.getLogger(UserDBStorage.class);
    private final JdbcTemplate jdbcTemplate;

    public UserDBStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll(UserDBService userDBService) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users", (resultSet, rowNumber) -> userDBService.getUser(resultSet));
        log.info("Current user amount is {}", users.size());
        return users;
    }

    public User create(User user) throws ValidationException {
        if (user.getName() == null || Objects.equals(user.getName(), "")){user.setName(user.getLogin());}
        userValidation(user);
        jdbcTemplate.update("INSERT INTO users (login, name, email, birthday) VALUES (?, ?, ?, ?)",
                user.getLogin(), user.getName(), user.getEmail(), user.getBirthday());
            SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE login = ?", user.getLogin());
        if(userSet.next()){
            user.setId(userSet.getInt("id"));
            log.info("User has been created, ID: {}", user.getId());
        }
        return user;
    }

    public User update(User user) throws ValidationException {
        if (user.getName() == null || Objects.equals(user.getName(), "")){user.setName(user.getLogin());}
        userValidation(user);
        System.out.println(user.getId());
        SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE id = ?", user.getId());
        if(userSet.next()) {
            String sql = "MERGE INTO users (id, login, name, email, birthday) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getId(), user.getLogin(), user.getName(), user.getEmail(), user.getBirthday());
            log.info("User has been updated, ID: {}", user.getId());
            return user;
        } else {
            throw new UserNotFoundException("User id not found");
        }
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%

    public void userValidation(User user) throws ValidationException {
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("User login is empty or has empty symbols");
        }
        if (user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("User birth date is in future");
        }
    }

}

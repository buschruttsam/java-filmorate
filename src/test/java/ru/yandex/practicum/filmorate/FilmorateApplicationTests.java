package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmDBService;
import ru.yandex.practicum.filmorate.service.UserDBService;
import ru.yandex.practicum.filmorate.storage.FilmDBStorage;
import ru.yandex.practicum.filmorate.storage.UserDBStorage;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilmorateApplicationTests {

    private final UserDBService userDBService;
    private final UserDBStorage userDBStorage;
    private final FilmDBService filmDBService;
    private final FilmDBStorage filmDBStorage;
    User testUser1 = new User();
    User testUser2 = new User();
    Film testFilm1 = new Film();
    Film testFilm2 = new Film();

    @Test
    @BeforeAll
    public void testCreateUser() throws ValidationException {
        testUser1.setName("user_name_1");
        testUser1.setLogin("user_login_1");
        testUser1.setBirthday(LocalDate.parse("1980-05-05"));
        testUser1.setEmail("user_1@users.coma");
        testUser2.setName("user_name_2");
        testUser2.setLogin("user_login_2");
        testUser2.setBirthday(LocalDate.parse("1986-06-06"));
        testUser2.setEmail("user_2@users.coma");
        userDBStorage.create(testUser1);
        userDBStorage.create(testUser2);
    }

    @Test
    @BeforeAll
    public void testCreateFilm() throws ValidationException {
        Mpa mpa = new Mpa(); mpa.setId(1); mpa.setName("G");
        Genre genre = new Genre(); genre.setId(1); genre.setName("Комедия");
        testFilm1.setName("film_name_1");
        testFilm1.setDescription("film_description_1");
        testFilm1.setDuration(100);
        testFilm1.setReleaseDate(LocalDate.parse("1980-05-05"));
        testFilm1.setGenres(List.of(genre));
        testFilm1.setMpa(mpa);
        testFilm2.setName("film_name_2");
        testFilm2.setDescription("film_description_2");
        testFilm2.setDuration(90);
        testFilm2.setReleaseDate(LocalDate.parse("1985-10-10"));
        testFilm2.setGenres(List.of(genre));
        testFilm2.setMpa(mpa);
        filmDBStorage.create(testFilm1);
        filmDBStorage.create(testFilm2);
    }

    @Test
    public void testFindAllUsers(){
        List<User> users = userDBStorage.findAll(userDBService);
        Assertions.assertEquals(users.size(), 2);
    }

    @Test
    public void testFindAllFilms(){
        List<Film> films = filmDBStorage.getFilms(filmDBService);
        Assertions.assertEquals(films.size(), 2);
    }

    @Test
    public void testUpdateUserAndGetUserById() throws ValidationException {
        User user0 = userDBService.findById(1);
        Assertions.assertEquals(user0.getLogin(), "user_login_1");
        User testUser1New = testUser1;
        testUser1New.setLogin("user_login_1_new");
        userDBStorage.update(testUser1New);
        User user = userDBService.findById(1);
        Assertions.assertEquals(user.getLogin(), "user_login_1_new");
    }

    @Test
    public void testUpdateFilmAndGetFilmById() throws ValidationException {
        Film film0 = filmDBService.findById(1);
        Assertions.assertEquals(film0.getName(), "film_name_1");
        Film testFilm1New = testFilm1;
        testFilm1New.setName("film_name_1_new");
        filmDBStorage.update(testFilm1New);
        Film film = filmDBService.findById(1);
        Assertions.assertEquals(film.getName(), "film_name_1_new");
    }

    @Test
    public void testUserService() throws ValidationException {
        User testUser3 = new User();
        testUser3.setName("user_name_3");
        testUser3.setLogin("user_login_3");
        testUser3.setBirthday(LocalDate.parse("2000-05-05"));
        testUser3.setEmail("user_3@users.coma");
        userDBStorage.create(testUser3);
        userDBService.addFriend (1, 3);
        userDBService.addFriend (1, 2);
        userDBService.addFriend (2, 3);
        List<User> users = userDBService.getAllFriends (1, userDBStorage);
        Assertions.assertEquals(users.size(), 2);
        users = userDBService.getCommonFriends (1, 2, userDBStorage);
        Assertions.assertEquals(users.size(), 1);
        userDBService.removeFriend(1, 3);
        users = userDBService.getAllFriends (1, userDBStorage);
        Assertions.assertEquals(users.size(), 1);
        users = userDBService.getCommonFriends (1, 2, userDBStorage);
        Assertions.assertEquals(users.size(), 0);
    }

    @Test
    public void testGenres(){
        List<Genre> genres = filmDBService.getAllGenres();
        Assertions.assertEquals(genres.size(), 6);
    }

    @Test
    public void testMpa(){
        List<Mpa> mpas = filmDBService.getAllMpa();
        Mpa mpa = filmDBService.getMpaById(2);
        Assertions.assertEquals(mpas.size(), 5);
        Assertions.assertEquals(mpas.get(0).getName(), "G");
        Assertions.assertEquals(mpa.getName(), "PG");
    }

    @Test
    public void testFilmService(){
        filmDBService.addLike(1, 1);
        filmDBService.addLike(1, 2);
        filmDBService.addLike(2, 1);
        List<Film> films = filmDBService.getFiltered0Films(2);
        Assertions.assertEquals(films.size(), 2);
        Assertions.assertEquals(films.get(0).getName(), "film_name_1_new");
        filmDBService.addLike(2, 2);
        filmDBService.removeLike(1, 2);
        films = filmDBService.getFiltered0Films(2);
        Assertions.assertEquals(films.size(), 2);
        Assertions.assertEquals(films.get(0).getName(), "film_name_2");
    }
}

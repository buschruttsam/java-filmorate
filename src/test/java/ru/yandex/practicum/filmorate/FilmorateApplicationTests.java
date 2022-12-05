package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {

	@Test
	void contextLoads() {
	}

	/*
	@Test
	void addUserTest() {
		User user_1 = new User(1);
		user_1.setName("User_name_1");
		user_1.setEmail("test1@domain_never_used.ru");
		user_1.setLogin("user_1");
		user_1.setDate(LocalDate.now());
		User user_2 = new User(2);
		user_2.setName("User_name_2");
		user_2.setEmail("test2@domain_never_used.ru");
		user_2.setLogin("user_2");
		user_2.setDate(LocalDate.now());
		UserController userController = new UserController();
		Assertions.assertTrue(userController.getUsers().isEmpty());
		userController.create(user_1);
		userController.create(user_2);
		Assertions.assertEquals(userController.getUsers().size(), 2);
		Assertions.assertEquals(userController.getUsers().get(0).getName(), "User_name_1");
		Assertions.assertEquals(userController.getUsers().get(1).getEmail(), "test2@domain_never_used.ru");
	}

	@Test
	void updateUserTest() {
		User user_1 = new User(1);
		user_1.setName("User_name_1");
		user_1.setEmail("test1@domain_never_used.ru");
		user_1.setLogin("user_1");
		user_1.setDate(LocalDate.now());
		UserController userController = new UserController();
		Assertions.assertTrue(userController.getUsers().isEmpty());
		userController.create(user_1);
		Assertions.assertEquals(userController.getUsers().get(0).getEmail(), "test1@domain_never_used.ru");
		User user_1_upd = new User(1);
		user_1_upd.setName("User_name_1");
		user_1_upd.setEmail("new_test1@domain_never_used.ru");
		user_1_upd.setLogin("user_1");
		user_1_upd.setDate(LocalDate.now());
		userController.update(user_1_upd);
		Assertions.assertEquals(userController.getUsers().get(0).getEmail(), "new_test1@domain_never_used.ru");
	}

	@Test
	void crateInvalidEmailUserTest() {
		User user_no_valid_1 = new User(1);
		user_no_valid_1.setName("User_no_valid_name_1");
		user_no_valid_1.setEmail("no_valid_email.ru");
		user_no_valid_1.setLogin("user_1");
		user_no_valid_1.setDate(LocalDate.now());
		UserController userController = new UserController();
		userController.create(user_no_valid_1);
		Assertions.assertTrue(userController.getUsers().isEmpty());
		//assertThrows(ValidationException.class,() -> userController.create(user_no_valid_1));
	}

	@Test
	void updateInvalidLoginUserTest() {
		User user_1 = new User(1);
		user_1.setName("User_name_1");
		user_1.setEmail("test1@domain_never_used.ru");
		user_1.setLogin("user_1");
		user_1.setDate(LocalDate.now());
		UserController userController = new UserController();
		Assertions.assertTrue(userController.getUsers().isEmpty());
		userController.create(user_1);
		Assertions.assertEquals(userController.getUsers().get(0).getLogin(), "user_1");
		User user_1_upd = new User(1);
		user_1_upd.setName("");
		user_1_upd.setEmail("test1@domain_never_used.ru");
		user_1_upd.setLogin("user_1");
		user_1_upd.setDate(LocalDate.now());
		userController.update(user_1);
		Assertions.assertEquals(userController.getUsers().get(0).getLogin(), "user_1");
	}

	@Test
	void addFilmTest() {
		Film film_1 = new Film(1);
		film_1.setName("Film_name_1");
		film_1.setDescription("Film description n_1");
		film_1.setDuration(Duration.ofMinutes(90));
		film_1.setReleaseDate(LocalDate.of(1995, 12, 28));
		Film film_2 = new Film(2);
		film_2.setName("Film_name_2");
		film_2.setDescription("Film description n_2");
		film_2.setDuration(Duration.ofMinutes(85));
		film_2.setReleaseDate(LocalDate.of(2000, 12, 28));
		FilmController filmController = new FilmController();
		Assertions.assertTrue(filmController.findAll().isEmpty());
		filmController.create(film_1);
		filmController.create(film_2);
		Assertions.assertEquals(filmController.findAll().size(), 2);
		Assertions.assertEquals(filmController.findAll().get(0).getName(), "Film_name_1");
		Assertions.assertEquals(filmController.findAll().get(1).getDuration().toMinutes(), 85);
	}


	@Test
	void updateFilmTest() {
		Film film_1 = new Film(1);
		film_1.setName("Film_name_1");
		film_1.setDescription("Film description n_1");
		film_1.setDuration(Duration.ofMinutes(90));
		film_1.setReleaseDate(LocalDate.of(1995, 12, 28));
		FilmController filmController = new FilmController();
		Assertions.assertTrue(filmController.findAll().isEmpty());
		filmController.create(film_1);
		Assertions.assertEquals(filmController.findAll().get(0).getDuration().toMinutes(), 90);
		Film film_1_upd = new Film(1);
		film_1_upd.setName("Film_name_1");
		film_1_upd.setDescription("Film description n_1");
		film_1_upd.setDuration(Duration.ofMinutes(99));
		film_1_upd.setReleaseDate(LocalDate.of(1995, 12, 28));
		filmController.update(film_1_upd);
		Assertions.assertEquals(filmController.findAll().get(0).getDuration().toMinutes(), 99);
	}

	@Test
	void crateInvalidDurationFilmTest() {
		Film film_no_valid_1 = new Film(1);
		film_no_valid_1.setName("Film_name_1");
		film_no_valid_1.setDescription("Film description n_1");
		film_no_valid_1.setDuration(Duration.ofMinutes(0));
		film_no_valid_1.setReleaseDate(LocalDate.of(1995, 12, 28));
		FilmController filmController = new FilmController();
		filmController.create(film_no_valid_1);
		Assertions.assertTrue(filmController.findAll().isEmpty());
	}

	@Test
	void updateInvalidDateFilmTest() {
		Film film_1 = new Film(1);
		film_1.setName("Film_name_1");
		film_1.setDescription("Film description n_1");
		film_1.setDuration(Duration.ofMinutes(90));
		film_1.setReleaseDate(LocalDate.of(1995, 12, 28));
		FilmController filmController = new FilmController();
		Assertions.assertTrue(filmController.findAll().isEmpty());
		filmController.create(film_1);
		Assertions.assertEquals(filmController.findAll().get(0).getDuration().toMinutes(), 90);
		Film film_1_upd = new Film(1);
		film_1_upd.setName("Film_name_1");
		film_1_upd.setDescription("Film description n_1");
		film_1_upd.setDuration(Duration.ofMinutes(99));
		film_1_upd.setReleaseDate(LocalDate.of(1995, 12, 27));
		filmController.update(film_1_upd);
		Assertions.assertEquals(filmController.findAll().get(0).getReleaseDate(), LocalDate.of(1995, 12, 28));
	}
*/
}

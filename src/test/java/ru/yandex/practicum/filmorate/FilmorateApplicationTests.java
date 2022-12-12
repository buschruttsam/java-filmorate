package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {

	@Test
	void contextLoads() {
	}

	/*

	@Test
	void addUserTest() throws ValidationException {
		User user_1 = new User();
		user_1.setName("User_name_1");
		user_1.setEmail("test1@domain_never_used.ru");
		user_1.setLogin("user_1");
		user_1.setBirthday(LocalDate.now());
		User user_2 = new User();
		user_2.setName("User_name_2");
		user_2.setEmail("test2@domain_never_used.ru");
		user_2.setLogin("user_2");
		user_2.setBirthday(LocalDate.now());
		UserController userController = new UserController();
		Assertions.assertTrue(userController.findAll().isEmpty());
		userController.create(user_1);
		userController.create(user_2);
		Assertions.assertEquals(userController.findAll().size(), 2);
		Assertions.assertEquals(userController.findAll().get(0).getName(), "User_name_1");
		Assertions.assertEquals(userController.findAll().get(1).getEmail(), "test2@domain_never_used.ru");
	}

	@Test
	void updateUserTest() throws ValidationException {
		User user_1 = new User();
		user_1.setName("User_name_1");
		user_1.setEmail("test1@domain_never_used.ru");
		user_1.setLogin("user_1");
		user_1.setBirthday(LocalDate.now());
		UserController userController = new UserController();
		Assertions.assertTrue(userController.findAll().isEmpty());
		userController.create(user_1);
		Assertions.assertEquals(userController.findAll().get(0).getEmail(), "test1@domain_never_used.ru");
		User user_1_upd = new User();
		user_1_upd.setId(1);
		user_1_upd.setName("User_name_1");
		user_1_upd.setEmail("new_test1@domain_never_used.ru");
		user_1_upd.setLogin("user_1");
		user_1_upd.setBirthday(LocalDate.now());
		userController.update(user_1_upd);
		Assertions.assertEquals(userController.findAll().get(0).getEmail(), "new_test1@domain_never_used.ru");
	}

	@Test
	void updateInvalidLoginUserTest() throws ValidationException {
		User user_1 = new User();
		user_1.setName("User_name_1");
		user_1.setEmail("test1@domain_never_used.ru");
		user_1.setLogin("user_1");
		user_1.setBirthday(LocalDate.now());
		UserController userController = new UserController();
		Assertions.assertTrue(userController.findAll().isEmpty());
		userController.create(user_1);
		Assertions.assertEquals(userController.findAll().get(0).getLogin(), "user_1");
		User user_1_upd = new User();
		user_1_upd.setName("");
		user_1_upd.setEmail("test1@domain_never_used.ru");
		user_1_upd.setLogin("user_1");
		user_1_upd.setBirthday(LocalDate.now());
		userController.update(user_1);
		Assertions.assertEquals(userController.findAll().get(0).getLogin(), "user_1");
	}

	@Test
	void addFilmTest() throws ValidationException {
		Film film_1 = new Film();
		film_1.setName("Film_name_1");
		film_1.setDescription("Film description n_1");
		film_1.setDuration(90);
		film_1.setReleaseDate(LocalDate.of(1995, 12, 28));
		Film film_2 = new Film();
		film_2.setName("Film_name_2");
		film_2.setDescription("Film description n_2");
		film_2.setDuration(85);
		film_2.setReleaseDate(LocalDate.of(2000, 12, 28));
		FilmController filmController = new FilmController();
		Assertions.assertTrue(filmController.findAll().isEmpty());
		filmController.create(film_1);
		filmController.create(film_2);
		Assertions.assertEquals(filmController.findAll().size(), 2);
		Assertions.assertEquals(filmController.findAll().get(0).getName(), "Film_name_1");
		Assertions.assertEquals(filmController.findAll().get(1).getDuration(), 85);
	}


	@Test
	void updateFilmTest() throws ValidationException {
		Film film_1 = new Film();
		film_1.setName("Film_name_1");
		film_1.setDescription("Film description n_1");
		film_1.setDuration(90);
		film_1.setReleaseDate(LocalDate.of(1995, 12, 28));
		FilmController filmController = new FilmController();
		Assertions.assertTrue(filmController.findAll().isEmpty());
		filmController.create(film_1);
		Assertions.assertEquals(filmController.findAll().get(0).getDuration(), 90);
		Film film_1_upd = new Film();
		film_1_upd.setId(1);
		film_1_upd.setName("Film_name_1");
		film_1_upd.setDescription("Film description n_1");
		film_1_upd.setDuration(99);
		film_1_upd.setReleaseDate(LocalDate.of(1995, 12, 28));
		filmController.update(film_1_upd);
		Assertions.assertEquals(filmController.findAll().get(0).getDuration(), 99);
	}

	@Test
	void crateInvalidDurationFilmTest() {
		Film film_no_valid_1 = new Film();
		film_no_valid_1.setName("Film_name_1");
		film_no_valid_1.setDescription("Film description n_1");
		film_no_valid_1.setDuration(0);
		film_no_valid_1.setReleaseDate(LocalDate.of(1995, 12, 28));
		FilmController filmController = new FilmController();
		assertThrows(ValidationException.class,() -> filmController.create(film_no_valid_1));
	}

	@Test
	void updateInvalidDateFilmTest() throws ValidationException {
		Film film_1 = new Film();
		film_1.setName("Film_name_1");
		film_1.setDescription("Film description n_1");
		film_1.setDuration(90);
		film_1.setReleaseDate(LocalDate.of(1995, 12, 28));
		FilmController filmController = new FilmController();
		Assertions.assertTrue(filmController.findAll().isEmpty());
		filmController.create(film_1);
		Assertions.assertEquals(filmController.findAll().get(0).getDuration(), 90);
		Film film_1_upd = new Film();
		film_1_upd.setName("Film_name_1");
		film_1_upd.setDescription("Film description n_1");
		film_1_upd.setDuration(99);
		film_1_upd.setReleaseDate(LocalDate.of(1995, 12, 27));
		assertThrows(ValidationException.class,() -> filmController.update(film_1_upd));
	}
*/
}

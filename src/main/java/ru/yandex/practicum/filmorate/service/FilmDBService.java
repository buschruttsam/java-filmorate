package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.domain.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Data
@Repository
public class FilmDBService implements FilmService {

    private final JdbcTemplate jdbcTemplate;
    public FilmDBService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film findById(int id) throws ValidationException {
        SqlRowSet filmSet = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE id = ?", id);
        if(filmSet.next()){
            Film film = getFilm(filmSet);
            film.setGenres(jdbcTemplate.query("SELECT * FROM film_genres WHERE film_id =?",
                    (resultSet, rowNumber) -> getGenre(resultSet), film.getId()));
            return film;
        } else {
            throw new ValidationException("Film not found");
        }
    }

    @Override
    public List<Film> getFiltered0Films(int limit) {
        String sql = "SELECT film_id, COUNT(user_id) AS likes FROM film_likes " +
                "LEFT JOIN films ON films.id = film_likes.film_id " +
                "GROUP BY film_id ORDER BY likes DESC LIMIT ?";
        List<Film> films = jdbcTemplate.query(sql,
                (resultSet, rowNumber) -> getFilm(resultSet), limit);
        for (Film film : films){
            film.setGenres(jdbcTemplate.query("SELECT * FROM film_genres WHERE film_id =?",
                    (resultSet, rowNumber) -> getGenre(resultSet), film.getId()));
        }
        return films;
    }

    @Override
    public List<String> getAllGenres() {
        return null;
    }

    @Override
    public GenreResponse getGenresById(int id) {
        return null;
    }

    @Override
    public List<String> getAllMpa() {
        return null;
    }

    @Override
    public MpaResponse getMpaById(int id) {
        return null;
    }

    @Override
    public void addLike(int id, int userId) {

    }

    @Override
    public void removeLike(int id, int userId) {

    }

    // %%%%%%%%%% %%%%%%%%%% additional methods %%%%%%%%%% %%%%%%%%%%

    public Film getFilm(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
        film.setDuration(resultSet.getInt("duration"));
        Mpa mpa = new Mpa();
        mpa.setId(resultSet.getInt("mpa_rating_id"));
        film.setMpa(mpa);
        return film;
    }

    public Genre getGenre(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("genre_id"));
        return genre;
    }

    public Film getFilm(SqlRowSet filmSet) {
        Film film = new Film();
        film.setId(filmSet.getInt("id"));
        film.setName(filmSet.getString("name"));
        film.setDescription(filmSet.getString("description"));
        film.setReleaseDate(Objects.requireNonNull(filmSet.getDate("release_date")).toLocalDate());
        film.setDuration(filmSet.getInt("duration"));
        Mpa mpa0 = new Mpa();
        mpa0.setId(filmSet.getInt("mpa_rating_id"));
        film.setMpa(mpa0);
        return film;
    }

}

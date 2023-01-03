package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.domain.exeptions.UserNotFoundException;
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

    public List<FilmToLike> getLikesTable(){
        List<FilmToLike> table_ = jdbcTemplate.query("SELECT * FROM film_likes", (resultSet, rowNumber) -> getRow(resultSet));
        return table_;
    }

    FilmToLike getRow(ResultSet resultSet) throws SQLException {
        FilmToLike ftl = new FilmToLike();
        ftl.setFilm_id(resultSet.getInt("film_id"));
        ftl.setFilm_likes(resultSet.getInt("user_id"));
        return ftl;
    }


    @Override
    public Film findById(int id) throws UserNotFoundException {
        SqlRowSet filmSet = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE id = ?", id);
        if(filmSet.next()){
            Film film = getFilm(filmSet);
            film.setGenres(jdbcTemplate.query("SELECT * FROM film_genres WHERE film_id =?", (resultSet, rowNumber) -> getGenre(resultSet), film.getId()));
            return film;
        } else {
            throw new UserNotFoundException("Film not found");
        }
    }

    @Override
    public List<Film> getFiltered0Films(int limit) {
        System.out.println("FLAG-- limit-- " + limit);
        getLikesTable();
        System.out.println("FLAG-- table_size-- " + getLikesTable().size());
        String sql = "SELECT film_id, COUNT(user_id) AS likes FROM film_likes LEFT JOIN films ON films.id = film_likes.film_id GROUP BY film_id ORDER BY likes DESC LIMIT ?";
        List<Film> films = jdbcTemplate.query(sql, (resultSet, rowNumber) -> getFilmById(resultSet), limit);
        for (Film film : films){
            film.setGenres(jdbcTemplate.query("SELECT * FROM film_genres WHERE film_id =?", (resultSet, rowNumber) -> getGenre(resultSet), film.getId()));
        }
        return films;
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT * FROM genres", (resultSet, rowNumber) -> getRawGenre(resultSet));
    }

    @Override
    public Genre getGenresById(int id) {
        SqlRowSet genreSet = jdbcTemplate.queryForRowSet("SELECT * FROM genres WHERE id = ?", id);
        if (genreSet.next()){
            Genre genre = new Genre();
            genre.setId(genreSet.getInt("id"));
            genre.setName(genreSet.getString("genre_name"));
            return genre;
        } else {
            throw new UserNotFoundException("m:getGenresById genre id not found");
        }
    }

    @Override
    public List<Mpa> getAllMpa() {
        return jdbcTemplate.query("SELECT * FROM film_mpa_ratings", (resultSet, rowNumber) -> getRawMpa(resultSet));
    }

    @Override
    public Mpa getMpaById(int id) {
        SqlRowSet mpaSet = jdbcTemplate.queryForRowSet("SELECT * FROM film_mpa_ratings WHERE id = ?", id);
        if (mpaSet.next()){
            Mpa mpa = new Mpa();
            mpa.setId(mpaSet.getInt("id"));
            mpa.setName(mpaSet.getString("rating_name"));
            return mpa;
        } else {
            throw new UserNotFoundException("m:getMpaById mpa id not found");
        }
    }

    @Override
    public void addLike(int filmId, int userId) {
        if (checkIfFilmIdExist(filmId) && checkIfUserIdExist(userId)){
            SqlRowSet likeSet = jdbcTemplate.queryForRowSet("SELECT * FROM film_likes WHERE film_id = ? AND user_id = ?", filmId, userId);
            if(!likeSet.next()){
                jdbcTemplate.update("INSERT INTO film_likes (film_id, user_id) VALUES (?, ?)", filmId, userId);
            }
        } else {
            throw new UserNotFoundException("m:addLike film or user not found");
        }
    }

    @Override
    public void removeLike(int filmId, int userId) {
        if (checkIfFilmIdExist(filmId) && checkIfUserIdExist(userId)){
            SqlRowSet likeSet = jdbcTemplate.queryForRowSet("SELECT * FROM film_likes WHERE film_id = ? AND user_id = ?", filmId, userId);
            if(likeSet.next()){
                jdbcTemplate.update("DELETE FROM film_likes WHERE film_id = ? AND user_id = ?", filmId, userId);
            } else {
                throw new UserNotFoundException("m:removeLike film-user pair not found");
            }
        } else {
            throw new UserNotFoundException("m:removeLike film or user not found");
        }
    }

    // %%%%%%%%%% %%%%%%%%%% additional methods %%%%%%%%%% %%%%%%%%%%

    boolean checkIfUserIdExist (int id) {
        SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE id = ?", id);
        return userSet.next();
    }

    boolean checkIfFilmIdExist (int id) {
        SqlRowSet userSet = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE id = ?", id);
        return userSet.next();
    }

    public Genre getGenre(ResultSet resultSet) throws SQLException {
        return getGenresById(resultSet.getInt("genre_id"));
    }

    public Genre getRawGenre(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("id"));
        genre.setName(resultSet.getString("genre_name"));
        return genre;
    }

    public Mpa getRawMpa(ResultSet resultSet) throws SQLException {
        Mpa mpa = new Mpa();
        mpa.setId(resultSet.getInt("id"));
        mpa.setName(resultSet.getString("rating_name"));
        return mpa;
    }

    public Film getFilmById(ResultSet resultSet) throws SQLException {
        System.out.println("FLAG03-- ");
        System.out.println("FLAG-- film_id-- " + resultSet.getInt("film_id"));
        return findById(resultSet.getInt("film_id"));
    }

    public Film getFilm(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
        film.setDuration(resultSet.getInt("duration"));
        film.setMpa(getMpaById(resultSet.getInt("mpa_rating_id")));
        return film;
    }

    public Film getFilm(SqlRowSet filmSet) {
        Film film = new Film();
        film.setId(filmSet.getInt("id"));
        film.setName(filmSet.getString("name"));
        film.setDescription(filmSet.getString("description"));
        film.setReleaseDate(Objects.requireNonNull(filmSet.getDate("release_date")).toLocalDate());
        film.setDuration(filmSet.getInt("duration"));
        film.setMpa(getMpaById(filmSet.getInt("mpa_rating_id")));
        return film;
    }

}

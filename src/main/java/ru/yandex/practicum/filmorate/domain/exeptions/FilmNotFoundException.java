package ru.yandex.practicum.filmorate.domain.exeptions;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException (){ super(); }

    public FilmNotFoundException (String msg){
        super(msg);
    }

}

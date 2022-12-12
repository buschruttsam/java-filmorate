package ru.yandex.practicum.filmorate.domain.exeptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException (){ super(); }

    public UserNotFoundException (String msg){
        super(msg);
    }
}

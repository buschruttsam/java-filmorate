package ru.yandex.practicum.filmorate.domain.exeptions;

public class ValidationException extends Throwable {
    public ValidationException() {
    }

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ValidationException(final Throwable cause) {
        super(cause);
    }
}

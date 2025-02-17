package app.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    USER_NOT_FOUND("User not found!"),
    USER_ALREADY_EXISTS("User already exists!"),
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password!"),

    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}

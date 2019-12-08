package com.mobila.project.today;

public class UncheckedTodayException extends RuntimeException {
    public UncheckedTodayException() {
        super();
    }

    public UncheckedTodayException(String message) {
        super(message);
    }

    public UncheckedTodayException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.mobila.project.today;

public class TodayException extends Exception {
    public TodayException() {
        super();
    }

    public TodayException(String message) {
        super(message);
    }

    public TodayException(String message, Throwable cause) {
        super(message, cause);
    }
}

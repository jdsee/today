package com.mobila.project.today.dataProviding;

import com.mobila.project.today.TodayException;

public class DataKeyNotFoundException extends TodayException {
    public DataKeyNotFoundException() {

    }

    public DataKeyNotFoundException(String message) {
        super(message);
    }

    public DataKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

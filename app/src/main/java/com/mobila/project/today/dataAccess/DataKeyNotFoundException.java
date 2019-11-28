package com.mobila.project.today.dataAccess;

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

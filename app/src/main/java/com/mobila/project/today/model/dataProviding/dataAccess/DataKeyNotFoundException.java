package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.UncheckedTodayException;

public class DataKeyNotFoundException extends UncheckedTodayException {
    public static final String NO_ENTRY_MSG = "No entries found for the given key";

    //TODO find out if this exception should be unchecked. dataKey could be untraceable at runtime

    public DataKeyNotFoundException() {
    }

    public DataKeyNotFoundException(String message) {
        super(message);
    }

    public DataKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

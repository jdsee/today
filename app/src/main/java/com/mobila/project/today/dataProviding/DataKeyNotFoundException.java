package com.mobila.project.today.dataProviding;

import com.mobila.project.today.UncheckedTodayException;

public class DataKeyNotFoundException extends UncheckedTodayException {

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

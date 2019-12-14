package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.UncheckedTodayException;

class KeyAlreadyAssignedException extends UncheckedTodayException {
    public KeyAlreadyAssignedException() {
        super();
    }

    public KeyAlreadyAssignedException(String message) {
        super(message);
    }

    public KeyAlreadyAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}

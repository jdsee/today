package com.mobila.project.today.dataProviding;

import com.mobila.project.today.TodayException;

public class OperationNotSupportedInActualContextException extends TodayException {
    public OperationNotSupportedInActualContextException() {
        super();
    }

    public OperationNotSupportedInActualContextException(String message) {
        super(message);
    }

    public OperationNotSupportedInActualContextException(String message, Throwable cause) {
        super(message, cause);
    }
}

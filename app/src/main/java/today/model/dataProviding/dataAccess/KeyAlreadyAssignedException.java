package today.model.dataProviding.dataAccess;

import today.UncheckedTodayException;

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

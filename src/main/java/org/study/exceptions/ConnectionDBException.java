package org.study.exceptions;

public class ConnectionDBException extends RuntimeException {

    public ConnectionDBException(String message) {
        super(message);
    }

    public ConnectionDBException(String message, Throwable cause) {
        super(message, cause);
    }
}

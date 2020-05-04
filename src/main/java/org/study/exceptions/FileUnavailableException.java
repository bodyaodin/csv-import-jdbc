package org.study.exceptions;

public class FileUnavailableException extends RuntimeException {

    public FileUnavailableException(String message) {
        super(message);
    }

    public FileUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}

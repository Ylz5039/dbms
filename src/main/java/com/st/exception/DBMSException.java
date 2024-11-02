package com.st.exception;

public class DBMSException extends RuntimeException {

    public DBMSException(String message) {
        super(message);
    }

    public DBMSException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.carrotfriend.exception;

public class CookieNotFound extends RuntimeException{
    public CookieNotFound() {
        super();
    }

    public CookieNotFound(String message) {
        super(message);
    }

    public CookieNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieNotFound(Throwable cause) {
        super(cause);
    }

    protected CookieNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

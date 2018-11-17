package com.dawid.chat.api;

/**
 * Created by Dawid on 17.11.2018 at 14:57.
 */
public class ChatException extends RuntimeException {
    public ChatException() {
        super();
    }

    public ChatException(String message) {
        super(message);
    }

    public ChatException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatException(Throwable cause) {
        super(cause);
    }

    protected ChatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

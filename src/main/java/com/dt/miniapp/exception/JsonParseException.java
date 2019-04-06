package com.dt.miniapp.exception;

/**
 * @author chenlei
 * @date 2018-08-31
 */
public class JsonParseException extends RuntimeException {

    public JsonParseException() {
        super();
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.github.chenzm77.auth.types.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final String code;

    public AppException(String code, String message) {
        super(message);
        this.code = code;
    }

    public AppException(String message) {
        super(message);
        this.code = "9999";
    }
}

package com.github.chenzm77.goods.types.exception;

import com.github.chenzm77.goods.types.enums.ResponseCode;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String code;

    public AppException(String message) {
        super(message);
        this.code = ResponseCode.SYSTEM_ERROR.getCode();
    }

    public AppException(String code, String message) {
        super(message);
        this.code = code;
    }
}

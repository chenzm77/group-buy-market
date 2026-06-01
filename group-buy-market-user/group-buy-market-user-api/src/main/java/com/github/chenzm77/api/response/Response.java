package com.github.chenzm77.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String msg;
    private T data;

    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .code("0000")
                .msg("success")
                .data(data)
                .build();
    }

    public static <T> Response<T> fail(String code, String msg) {
        return Response.<T>builder()
                .code(code)
                .msg(msg)
                .build();
    }
}
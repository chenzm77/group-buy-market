package com.github.chenzm77.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResponseCode {

    SUCCESS("0000", "成功"),
    UNKNOWN_ERROR("9999", "未知错误"),
    PARAM_ERROR("0001", "参数错误"),
    USER_NOT_FOUND("1001", "用户不存在"),
    ADDRESS_NOT_FOUND("2001", "地址不存在"),
    VERIFY_CODE_ERROR("3001", "验证码错误"),
    TOKEN_REVOKED("4001", "Token 已失效"),
    SESSION_EXPIRED("4002", "会话已过期"),
    USER_BANNED("1002", "用户已禁用"),
    VERIFY_CODE_TOO_FREQUENT("3002", "验证码发送过于频繁"),
    ;

    private String code;
    private String info;

}
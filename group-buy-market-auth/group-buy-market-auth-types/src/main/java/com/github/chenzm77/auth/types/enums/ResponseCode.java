package com.github.chenzm77.auth.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS("0000", "成功"),
    PARAM_ERROR("0001", "参数错误"),
    UNKNOWN_ERROR("9999", "未知错误"),
    VERIFY_CODE_ERROR("3001", "验证码错误"),
    VERIFY_CODE_SEND_ERROR("3002", "验证码发送失败"),
    USER_NOT_FOUND("1001", "用户不存在，请注册"),
    USER_ALREADY_EXISTS("1003", "用户已注册"),
    USER_BANNED("1002", "用户已禁用"),
    SESSION_EXPIRED("4002", "会话已过期"),
    REFRESH_TOKEN_INVALID("4003", "刷新令牌无效，请重新登录"),
    RPC_CONTEXT_ERROR("5001", "RPC 上下文异常");

    private final String code;
    private final String info;
}

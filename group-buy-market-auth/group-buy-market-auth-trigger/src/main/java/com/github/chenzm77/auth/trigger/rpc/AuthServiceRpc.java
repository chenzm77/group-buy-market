package com.github.chenzm77.auth.trigger.rpc;

import com.github.chenzm77.auth.api.IAuthService;
import com.github.chenzm77.auth.api.dto.AuthSessionResponseDTO;
import com.github.chenzm77.auth.api.dto.PhoneCodeAuthRequestDTO;
import com.github.chenzm77.auth.api.dto.SendVerifyCodeRequestDTO;
import com.github.chenzm77.auth.api.response.Response;
import com.github.chenzm77.auth.app.session.AuthApplicationService;
import com.github.chenzm77.auth.types.enums.ResponseCode;
import com.github.chenzm77.auth.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@Slf4j
@DubboService(version = "1.0.0")
public class AuthServiceRpc implements IAuthService {

    @Resource
    private AuthApplicationService authApplicationService;

    @Resource
    private RpcContextCookieReader rpcContextCookieReader;

    @Override
    public Response<Void> sendVerifyCode(SendVerifyCodeRequestDTO request) {
        try {
            authApplicationService.sendVerifyCode(request.getPhone());
            return Response.success(null);
        } catch (AppException e) {
            return Response.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("send verify code failed", e);
            return Response.fail(ResponseCode.UNKNOWN_ERROR.getCode(), ResponseCode.UNKNOWN_ERROR.getInfo());
        }
    }

    @Override
    public Response<AuthSessionResponseDTO> login(PhoneCodeAuthRequestDTO request) {
        try {
            return Response.success(authApplicationService.login(request.getPhone(), request.getCode()));
        } catch (AppException e) {
            return Response.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("login failed", e);
            return Response.fail(ResponseCode.UNKNOWN_ERROR.getCode(), ResponseCode.UNKNOWN_ERROR.getInfo());
        }
    }

    @Override
    public Response<AuthSessionResponseDTO> register(PhoneCodeAuthRequestDTO request) {
        try {
            return Response.success(authApplicationService.register(request.getPhone(), request.getCode()));
        } catch (AppException e) {
            return Response.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("register failed", e);
            return Response.fail(ResponseCode.UNKNOWN_ERROR.getCode(), ResponseCode.UNKNOWN_ERROR.getInfo());
        }
    }

    @Override
    public Response<AuthSessionResponseDTO> refresh() {
        try {
            String refreshToken = rpcContextCookieReader.getRefreshToken();
            return Response.success(authApplicationService.refresh(refreshToken));
        } catch (AppException e) {
            return Response.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("refresh failed", e);
            return Response.fail(ResponseCode.UNKNOWN_ERROR.getCode(), ResponseCode.UNKNOWN_ERROR.getInfo());
        }
    }

    @Override
    public Response<Void> logout() {
        try {
            String sessionId = rpcContextCookieReader.getSessionId();
            String refreshToken = rpcContextCookieReader.getRefreshToken();
            authApplicationService.logout(sessionId, refreshToken);
            return Response.success(null);
        } catch (AppException e) {
            return Response.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("logout failed", e);
            return Response.fail(ResponseCode.UNKNOWN_ERROR.getCode(), ResponseCode.UNKNOWN_ERROR.getInfo());
        }
    }
}

package com.github.chenzm77.auth.api;

import com.github.chenzm77.auth.api.dto.AuthSessionResponseDTO;
import com.github.chenzm77.auth.api.dto.PhoneCodeAuthRequestDTO;
import com.github.chenzm77.auth.api.dto.SendVerifyCodeRequestDTO;
import com.github.chenzm77.auth.api.response.Response;

public interface IAuthService {

    Response<Void> sendVerifyCode(SendVerifyCodeRequestDTO request);

    Response<AuthSessionResponseDTO> login(PhoneCodeAuthRequestDTO request);

    Response<AuthSessionResponseDTO> register(PhoneCodeAuthRequestDTO request);

    Response<AuthSessionResponseDTO> refresh();

    Response<Void> logout();
}

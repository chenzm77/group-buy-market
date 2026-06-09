package com.github.chenzm77.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthSessionResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String nickname;
    private String avatar;
    private String role;
    private String sessionId;
}

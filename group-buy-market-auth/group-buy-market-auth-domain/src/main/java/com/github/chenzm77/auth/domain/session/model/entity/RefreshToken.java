package com.github.chenzm77.auth.domain.session.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private String refreshTokenId;
    private Long userId;
    private Long issuedAt;
}

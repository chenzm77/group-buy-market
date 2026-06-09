package com.github.chenzm77.auth.domain.session.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String phone;
    private String nickname;
    private String avatar;
    private String role;
    private Integer status;

    public boolean isBanned() {
        return status != null && status == 0;
    }
}

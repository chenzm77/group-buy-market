package com.github.chenzm77.user.domain.user.model.entity;

import com.github.chenzm77.user.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;
    private String phone;
    private String nickname;
    private String avatar;
    private String role;
    private Integer status;

    public boolean isBanned() {
        return status != null && status == Constants.UserStatus.BANNED;
    }

    public void updateProfile(String nickname, String avatar) {
        if (nickname != null && !nickname.isEmpty()) {
            this.nickname = nickname;
        }
        if (avatar != null) {
            this.avatar = avatar;
        }
    }

    public void initNewUser() {
        this.nickname = "用户" + (this.phone != null ? this.phone.substring(Math.max(0, this.phone.length() - 4)) : "");
        this.avatar = "";
        this.role = Constants.UserRole.USER;
        this.status = Constants.UserStatus.NORMAL;
    }
}
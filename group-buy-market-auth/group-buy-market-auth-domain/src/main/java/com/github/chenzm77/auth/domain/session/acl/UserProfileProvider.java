package com.github.chenzm77.auth.domain.session.acl;

import com.github.chenzm77.auth.domain.session.model.valueobjects.PhoneNumber;
import com.github.chenzm77.auth.domain.session.model.valueobjects.UserPrincipal;

public interface UserProfileProvider {

    UserPrincipal findByPhone(PhoneNumber phoneNumber);

    UserPrincipal findById(Long userId);

    UserPrincipal createByPhone(PhoneNumber phoneNumber);
}

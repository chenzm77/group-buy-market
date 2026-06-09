package com.github.chenzm77.auth.domain.session.acl;

import com.github.chenzm77.auth.domain.session.model.valueobjects.PhoneNumber;

public interface SmsVerifyProvider {

    void sendVerifyCode(PhoneNumber phoneNumber);

    boolean checkVerifyCode(PhoneNumber phoneNumber, String code);
}

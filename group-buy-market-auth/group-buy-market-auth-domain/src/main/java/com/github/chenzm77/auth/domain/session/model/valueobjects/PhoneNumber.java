package com.github.chenzm77.auth.domain.session.model.valueobjects;

import com.github.chenzm77.auth.types.enums.ResponseCode;
import com.github.chenzm77.auth.types.exception.AppException;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.regex.Pattern;

@Value
public class PhoneNumber implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Pattern CN_MOBILE = Pattern.compile("^1[3-9]\\d{9}$");

    String value;

    public PhoneNumber(String value) {
        if (StringUtils.isBlank(value) || !CN_MOBILE.matcher(value).matches()) {
            throw new AppException(ResponseCode.PARAM_ERROR.getCode(), "手机号格式不正确");
        }
        this.value = value;
    }
}

package com.github.chenzm77.auth.infrastructure.adapter.acl;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.CheckSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.CheckSmsVerifyCodeResponse;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.github.chenzm77.auth.domain.session.acl.SmsVerifyProvider;
import com.github.chenzm77.auth.domain.session.model.valueobjects.PhoneNumber;
import com.github.chenzm77.auth.types.enums.ResponseCode;
import com.github.chenzm77.auth.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class AliyunPnvsSmsVerifyProvider implements SmsVerifyProvider {

    private static final String VERIFY_SUCCESS = "SUCCESS";

    @Resource
    private Client aliyunPnvsClient;

    @Value("${aliyun.pnvs.scheme-code:}")
    private String schemeCode;

    @Value("${aliyun.pnvs.template-code:SMS_198470532}")
    private String templateCode;

    @Override
    public void sendVerifyCode(PhoneNumber phoneNumber) {
        try {
            SendSmsVerifyCodeRequest request = new SendSmsVerifyCodeRequest();
            request.setPhoneNumber(phoneNumber.getValue());
            request.setSchemeName(schemeCode);
            request.setTemplateCode(templateCode);
            SendSmsVerifyCodeResponse response = aliyunPnvsClient.sendSmsVerifyCodeWithOptions(request, new RuntimeOptions());
            log.info("PNVS send verify code success, phone={}, requestId={}", phoneNumber.getValue(), response.getBody().getRequestId());
        } catch (TeaException e) {
            log.warn("PNVS send verify code business failed, phone={}, message={}", phoneNumber.getValue(), e.getMessage(), e);
            throw new AppException(ResponseCode.VERIFY_CODE_SEND_ERROR.getCode(), ResponseCode.VERIFY_CODE_SEND_ERROR.getInfo());
        } catch (Exception e) {
            log.error("PNVS send verify code system failed, phone={}", phoneNumber.getValue(), e);
            throw new AppException(ResponseCode.VERIFY_CODE_SEND_ERROR.getCode(), ResponseCode.VERIFY_CODE_SEND_ERROR.getInfo());
        }
    }

    @Override
    public boolean checkVerifyCode(PhoneNumber phoneNumber, String code) {
        try {
            CheckSmsVerifyCodeRequest request = new CheckSmsVerifyCodeRequest();
            request.setPhoneNumber(phoneNumber.getValue());
            request.setVerifyCode(code);
            request.setSchemeName(schemeCode);
            CheckSmsVerifyCodeResponse response = aliyunPnvsClient.checkSmsVerifyCodeWithOptions(request, new RuntimeOptions());
            return Boolean.TRUE.equals(response.getBody().getSuccess())
                    || (response.getBody().getModel() != null && VERIFY_SUCCESS.equals(response.getBody().getModel().getVerifyResult()));
        } catch (TeaException e) {
            log.warn("PNVS check verify code business failed, phone={}, message={}", phoneNumber.getValue(), e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("PNVS check verify code system failed, phone={}", phoneNumber.getValue(), e);
            return false;
        }
    }
}

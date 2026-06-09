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
public class SendVerifyCodeRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String phone;
}

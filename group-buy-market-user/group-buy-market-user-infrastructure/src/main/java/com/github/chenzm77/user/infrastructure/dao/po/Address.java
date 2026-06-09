package com.github.chenzm77.user.infrastructure.dao.po;

import lombok.Data;

import java.util.Date;

@Data
public class Address {

    private Long id;
    private Long userId;
    private String receiverName;
    private String receiverPhone;
    private String province;
    private String city;
    private String district;
    private String detailAddress;
    private Integer isDefault;
    private Date createTime;
    private Date updateTime;
}
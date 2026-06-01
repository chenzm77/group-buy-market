package com.github.chenzm77.infrastructure.dao.po;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;
    private String phone;
    private String nickname;
    private String avatar;
    private String role;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
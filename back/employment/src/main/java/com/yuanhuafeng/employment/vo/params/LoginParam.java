package com.yuanhuafeng.employment.vo.params;

import lombok.Data;

@Data
public class LoginParam {
    private String account;
    private String password;
    private Integer roleId;
}

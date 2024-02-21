package com.yuanhuafeng.employment.vo.params;

import lombok.Data;

@Data
public class ModifyPasswordParam {
    private Integer userId;
    private String oldPassword;
    private String newPassword;
}

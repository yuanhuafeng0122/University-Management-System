package com.yuanhuafeng.employment.vo;

import com.yuanhuafeng.employment.entity.Teacher;
import com.yuanhuafeng.employment.entity.User;
import lombok.Data;

@Data
public class UserAndTeacher {
    public User user;
    public Teacher teacher;
}

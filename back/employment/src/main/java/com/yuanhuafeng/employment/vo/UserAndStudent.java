package com.yuanhuafeng.employment.vo;

import com.yuanhuafeng.employment.entity.Student;
import com.yuanhuafeng.employment.entity.User;
import lombok.Data;

@Data
public class UserAndStudent {
    public User user;
    public Student student;
}

package com.yuanhuafeng.employment.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TeaStuVo {
    /**
     * 学生Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生学号
     */
    private String number;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    @TableField("idNumber")
    private String idNumber;

    /**
     * 性别
     */
    private String gender;

    /**
     * 宿舍号
     */
    private String dormitory;

    /**
     * 家庭地址
     */
    private String address;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级Id
     */
    private String className;

    /**
     * 学院
     */
    private String collegeName;

    /**
     * 辅导员Id
     */
    private Integer teacherId;

    /**
     * 是否就业
     */
    private Boolean isWork;

    /**
     * 头像
     */
    private String cover;
    /**
     * 头像
     */
    private String teacherName;
}

package com.yuanhuafeng.employment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-04
 */
@Data

public class EmployInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 就业信息表Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 学号
     */
    private String number;


    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 辅导员姓名
     */
    private String teacherName;

    /**
     * 学生专业
     */
    private String major;

    /**
     * 班级
     */
    private String classes;

    /**
     * 就业去向
     */
    @TableField("graduationGoto")
    private String graduationGoto;

    /**
     * 填写时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("fillInTime")
    private Date fillInTime;

    /**
     * 单位名称
     */
    @TableField("companyName")
    private String companyName;

    /**
     * 岗位
     */
    private String post;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String dtaAddress;
    /**
     * 所属学生Id
     */
    private Integer studentId;

    /**
     * 联系人
     */
    private String empPerson;

    /**
     * 联系人电话
     */
    private String empPhone;


}

package com.yuanhuafeng.employment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-04
 */
@Data

public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 辅导员Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 辅导员姓名
     */
    private String name;

    /**
     * 辅导员联系电话
     */
    private String phone;

    /**
     * 所属学院
     */
    private String collegeName;

    /**
     * 办公地点
     */
    private String workRoom;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String cover;


}

package com.yuanhuafeng.employment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-12-05
 */
@Data

public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级表Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级专业
     */
    private String classMajor;

    /**
     * 所属辅导员Id
     */
    private String teaId;

    /**
     * 班级人数
     */
    private BigInteger fullStu;

    /**
     * 注册人数
     */
    private BigInteger trueStu;

    /**
     * 填写人数
     */
    private BigInteger fillStu;
    /**
     * 未注册人数
     */
    private BigInteger notTrueStu;

    /**
     * 未填写人数
     */
    private BigInteger notFillStu;

    /**
     * 已注册未填写
     */
    private BigInteger resNotFill;
    /**
     * 学院
     */
    private String collegeName;

}

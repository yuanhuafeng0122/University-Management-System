package com.yuanhuafeng.employment.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigInteger;
@Data
public class ClassTeaVo {
    /**
     * 班级表Id
     */
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
    /**
     * 老师
     */
    private String teacherName;
}

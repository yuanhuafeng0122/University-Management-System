package com.yuanhuafeng.employment.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
@Data

public class Recruit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 招聘id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业联系电话
     */
    private String companyPhone;

    /**
     * 岗位
     */
    private String post;

    /**
     * 薪水
     */
    private String salary;

    /**
     * 学历要求
     */
    private String education;

    /**
     * 职位要求
     */
    private String jobRequirements;

    /**
     * 发布时间
     */
    private Date releaseTime;

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
     * 发布者Id
     */
    private String pubId;

}

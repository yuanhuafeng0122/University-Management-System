package com.yuanhuafeng.employment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2021-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;
    /**
     * 不包含html内容
     */
    private String contentH;

    /**
     * 发布者Id
     */
    private String teaId;

    /**
     * 发布时间
     */
    private Date time;

    /**
     * 图片
     */
    private String cover;


}

package com.yuanhuafeng.employment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-11-03
 */
@Data

public class College implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学院_id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学院名称
     */
    private String name;

    /**
     * 学院描述
     */
    private String description;


}

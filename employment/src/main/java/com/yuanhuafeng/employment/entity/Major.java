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
 * @since 2021-11-03
 */
@Data

public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 专业Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属学院
     */
    @TableField("collegeId")
    private Integer collegeId;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 专业描述
     */
    private String description;


}

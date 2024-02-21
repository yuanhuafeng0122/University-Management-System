package com.yuanhuafeng.employment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 区县信息表
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
@Data

public class Areas implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 区县编码
     */
    private String areaId;

    /**
     * 区县名称
     */
    private String area;

    /**
     * 所属城市编码
     */
    private String cityId;


}

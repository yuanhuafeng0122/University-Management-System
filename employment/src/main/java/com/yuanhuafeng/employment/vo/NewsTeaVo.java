package com.yuanhuafeng.employment.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class NewsTeaVo {
    /**
     * Id
     */
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

    private String teacherName;
}

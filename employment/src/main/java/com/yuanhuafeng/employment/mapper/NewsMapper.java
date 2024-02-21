package com.yuanhuafeng.employment.mapper;

import com.yuanhuafeng.employment.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuanhuafeng.employment.vo.NewsTeaVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-12-18
 */
public interface NewsMapper extends BaseMapper<News> {

    List<NewsTeaVo> findAllNews();

    List<NewsTeaVo> findNew(String title);

    List<News> findNesByTeaId(Integer teaId);
}

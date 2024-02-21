package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-12-18
 */
public interface NewsService extends IService<News> {

    Result addNews(News news);

    Result findNesByTeaId(Integer teaId);

    Result modifyNews(News news);

    Result deleteNewsById(Integer id);

    Result findNews(String title);

    Result findAllNews();

    Result findNewsTea(String title);

    Result findNesAllByTeaId(Integer teaId);


    Result findNewsById(Integer id);
}

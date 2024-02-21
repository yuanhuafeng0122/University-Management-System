package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yuanhuafeng.employment.config.ErrorCode;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.News;
import com.yuanhuafeng.employment.mapper.NewsMapper;
import com.yuanhuafeng.employment.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuanhuafeng.employment.vo.NewsTeaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-12-18
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired(required = false)
    private NewsMapper newsMapper;

    @Override
    public Result addNews(News news) {

        int row = newsMapper.insert(news);

        if (row != 0){
            return Result.success();
        }else {
            return Result.fail(ErrorCode.ADDNEWS_ERROR.getCode(),ErrorCode.ADDNEWS_ERROR.getMsg());
        }


    }

    @Override
    public Result findNesByTeaId(Integer teaId) {


        List<News> list = newsMapper.findNesByTeaId(teaId);


        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.NOTFINDNEWS_ERROR.getCode(),ErrorCode.NOTFINDNEWS_ERROR.getMsg());
        }


    }

    @Override
    public Result modifyNews(News news) {

        int row = newsMapper.updateById(news);
        if (row!=0){
            return Result.success();
        }else {
            return Result.fail(ErrorCode.UPDATA_ERROR.getCode(),ErrorCode.UPDATA_ERROR.getMsg());
        }

    }

    @Override
    public Result deleteNewsById(Integer id) {

        int row = newsMapper.deleteById(id);
        if (row!=0){
            return Result.success();
        }else {
            return Result.fail(ErrorCode.DEL_FAIL.getCode(),ErrorCode.DEL_FAIL.getMsg());
        }

    }

    @Override
    public Result findNews(String title) {

        LambdaQueryWrapper<News> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(News::getTitle,title);
        List<News>  list= newsMapper.selectList(lambdaQueryWrapper);

        LambdaQueryWrapper<News> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.like(News::getContent,title);
        List<News> list1 = newsMapper.selectList(lambdaQueryWrapper1);

        list.addAll(list1);

        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.NOTFINDNEWS_ERROR.getCode(),ErrorCode.NOTFINDNEWS_ERROR.getMsg());
        }


    }

    @Override
    public Result findAllNews() {

        List<NewsTeaVo> list = newsMapper.findAllNews();

        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.NOTFINDNEWS_ERROR.getCode(),ErrorCode.NOTFINDNEWS_ERROR.getMsg());
        }
    }

    @Override
    public Result findNewsTea(String title) {

        List<NewsTeaVo> list = newsMapper.findNew(title);

        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.NOTFINDNEWS_ERROR.getCode(),ErrorCode.NOTFINDNEWS_ERROR.getMsg());
        }


    }

    @Override
    public Result findNesAllByTeaId(Integer teaId) {

        LambdaQueryWrapper<News> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(News::getTeaId,teaId).or().eq(News::getTeaId,11);
        List<News> list = newsMapper.selectList(lambdaQueryWrapper);

        System.out.println("<<<<>>>>>1111");


        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.NOTFINDNEWS_ERROR.getCode(),ErrorCode.NOTFINDNEWS_ERROR.getMsg());
        }
    }

    @Override
    public Result findNewsById(Integer id) {

        News news = newsMapper.selectById(id);
        if (news!=null){
            return Result.success(news);
        }else {
            return Result.fail(ErrorCode.NOTFINDNEWS_ERROR.getCode(),ErrorCode.NOTFINDNEWS_ERROR.getMsg());
        }


    }

}

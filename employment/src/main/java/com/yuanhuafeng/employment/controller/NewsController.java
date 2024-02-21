package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.News;
import com.yuanhuafeng.employment.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-12-18
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/addNews")
    public Result save(@RequestBody News news){
        news.setTime(new Date());
        System.out.println(news);
        return newsService.addNews(news);
    }

    @GetMapping("/findNesByTeaId/{teaId}")
    public Result findNesByTeaId(@PathVariable Integer teaId){
        return newsService.findNesByTeaId(teaId);
    }
    @GetMapping("/findNesAllByTeaId/{teaId}")
    public Result findNesAllByTeaId(@PathVariable Integer teaId){
        return newsService.findNesAllByTeaId(teaId);
    }

    @PutMapping("/modifyNews")
    public Result modifyNews(@RequestBody News news){
        return  newsService.modifyNews(news);
    }

    @DeleteMapping("/deleteNewsById/{id}")
    public Result deleteNewsById(@PathVariable Integer id){
        return newsService.deleteNewsById(id);
    }

    @GetMapping("/findNews/{title}")
    public Result findNews(@PathVariable String title){
        return newsService.findNews(title);
    }

    @GetMapping("/findNewsTea/{title}")
    public Result findNewsTea(@PathVariable String title){
        return newsService.findNewsTea(title);
    }

    @GetMapping("/findAllNews")
    public Result findAllNews(){
        return newsService.findAllNews();
    }

    @GetMapping("/findNewsById/{id}")
    public Result findNewsById(@PathVariable Integer id){
        return newsService.findNewsById(id);
    }


}


package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.mapper.MajorMapper;
import com.yuanhuafeng.employment.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-03
 */
@RestController
@RequestMapping("/major")
public class MajorController {
    @Autowired
    private MajorService majorService;

    @GetMapping("/findMajorByCollege/{collegeId}")
    public Result findMajorByCollege(@PathVariable("collegeId") Integer collegeId){
        return majorService.findMajorByCollege(collegeId);
    }

    @GetMapping("/findMajorByCollegeName/{collegeName}")
    public Result findMajorByCollegeName(@PathVariable("collegeName") String collegeName){
        return majorService.findMajorByCollegeName(collegeName);
    }

    @GetMapping("/findAllMajor")
    public Result findAllMajor(){
        return majorService.findAllMajor();
    }

}


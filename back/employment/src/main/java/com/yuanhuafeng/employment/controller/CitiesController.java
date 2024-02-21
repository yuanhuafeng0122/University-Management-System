package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 城市信息表 前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/cities")
public class CitiesController {

    @Autowired
    private CitiesService citiesService;

    @GetMapping("/findAllCity/{provinceId}")
    public Result findAllCity(@PathVariable("provinceId") String provinceId){
        return citiesService.findAllCity(provinceId);
    }

    @GetMapping("/findAllCity1")
    public Result findAllCity1(){
        return citiesService.findAllCity1();
    }

}


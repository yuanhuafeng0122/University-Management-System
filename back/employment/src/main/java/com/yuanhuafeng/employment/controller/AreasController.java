package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.service.AreasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 区县信息表 前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/areas")
public class AreasController {

    @Autowired
    private AreasService areasService;

    @GetMapping("/findAllAre/{cityId}")
    public Result findAllAre(@PathVariable("cityId") String cityId){
        return areasService.findAllAre(cityId);
    }

    @GetMapping("/findAllArea")
    public Result findAllArea(){
        return areasService.findAllArea();
    }
}


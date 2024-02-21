package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/provinces")
public class ProvincesController {
    @Autowired
    private ProvincesService provincesService;

    @GetMapping("/findAllPro")
    public Result findAllPro(){
        return provincesService.findAllPro();
    }

    @GetMapping("/findAllArea")
    public Result findAllArea(){
        return provincesService.findAllArea();
    }

}


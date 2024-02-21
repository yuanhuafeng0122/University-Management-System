package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.College;
import com.yuanhuafeng.employment.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-03
 */
@RestController
@RequestMapping("/college")
public class CollegeController {
    @Autowired
    private CollegeService collegeService;

    @GetMapping("/allCollege")
    public Result findAllCollege(){
        return collegeService.findAllCollege();
    }

    @GetMapping("/findCollegeById/{id}")
    public Result findCollegeByMajor(@PathVariable("id") Integer id){
        return collegeService.findCollegeByMajor(id);
    }

}


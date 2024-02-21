package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Recruit;
import com.yuanhuafeng.employment.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @PostMapping("/addRecruit")
    public Result addRecruit(@RequestBody Recruit recruit){
        return recruitService.addRecruit(recruit);
    }

     @GetMapping("/findAllRec")
    public Result findAllRec(){
        return recruitService.findAllRec();
     }

    //根据岗位名称查找岗位
    @PostMapping("/findRecByPost/{post}")
    public Result findRecByPost(@PathVariable("post") String post){
        return recruitService.findRecByPost(post);
    }

    @DeleteMapping("/deleteRecById/{id}")
    public Result deleteRecById(@PathVariable("id") Integer id){
        return recruitService.deleteRecById(id);
    }

    //修改岗位信息
    @PutMapping("/modifyRec")
    public Result modifyRec(@RequestBody Recruit recruit){
        return recruitService.modifyRec(recruit);
    }

    //更具Id查找
    @PostMapping("/findRecById/{id}")
    public Result findRecById(@PathVariable("id") Integer id){
        return recruitService.findRecById(id);
    }

    //更具发布者的Id查询
    @GetMapping("/findRecByPubId/{pubId}")
    public Result findRecByPubId(@PathVariable("pubId") Integer pubId){
        return recruitService.findRecByPubId(pubId);
    }
}


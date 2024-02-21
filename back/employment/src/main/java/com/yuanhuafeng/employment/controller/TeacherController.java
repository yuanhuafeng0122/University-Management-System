package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Teacher;
import com.yuanhuafeng.employment.service.TeacherService;
import com.yuanhuafeng.employment.vo.params.TeaClaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-04
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/findAllTeaByCollegeName/{collegeName}")
    public Result findAllTeaByCollegeName(@PathVariable("collegeName") String name){
        return teacherService.findAllTeaByCollegeName(name);
    }

    //找所有的老师
    @GetMapping("/findAllTea")
    public Result findAllTea(){
        return teacherService.findAllTea();
    }

    //删除老师和对应的用户
    @DeleteMapping("/delTeaByAccount/{phone}")
    public Result delTeaByAccount (@PathVariable("phone") String phone){
        return teacherService.delTeaByAccount(phone);
    }

    //修改老师信息1.0
    @PutMapping("/modifyTea")
    public Result modifyTea(@RequestBody Teacher teacher){
        return teacherService.modifyTea(teacher);
    }

    //修改老师信息2.0
    @PutMapping("/modifyTeaByPhone/{phone}")
    public Result modifyTeaByPhone(@RequestBody Teacher teacher,@PathVariable("phone") String phone){
        return teacherService.modifyTeaByPhone(teacher,phone);
    }

    //更具手机号码找老师
    @PostMapping("/findTeaByPhone/{phone}")
    public Result findTeaByPhone(@PathVariable("phone") String phone){
        return teacherService.findTeaByPhone(phone);
    }

    //根据Id查询老师
    @GetMapping("/findTeaById/{id}")
    public Result findTeaById(@PathVariable("id") Integer id){
        return  teacherService.findTeaById(id);
    }

    @GetMapping("/findTeaById1/{id}")
    public Result findTeaById1(@PathVariable("id")Integer id){
        return  teacherService.findTeaById(id);
    }

    //找老师和和班级
    @PostMapping("/findTeaAndClass")
    public Result findTeaAndClass(@RequestBody TeaClaId teaClaId){
        return teacherService.findTeaAndClass(teaClaId);
    }

}


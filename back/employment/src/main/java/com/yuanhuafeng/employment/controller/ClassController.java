package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Class;
import com.yuanhuafeng.employment.mapper.ClassMapper;
import com.yuanhuafeng.employment.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-12-05
 */
@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private ClassService classService;
    @PostMapping("/addClass")
    public Result addClass(@RequestBody Class myClass){
        return classService.addClass(myClass);
    }

    @PostMapping("/findClassesByTeaId/{teaId}")
    public Result findClassesByTeaId(@PathVariable("teaId") String teaId){
        return classService.findClassesByTeaId(teaId);
    }

    @GetMapping("/findClassByMajor/{majorName}")
    public Result findClassByMajor(@PathVariable("majorName") String majorName){
        return classService.findClassByMajor(majorName);
    }

    @GetMapping("/findTeaByClassName/{className}")
    public Result findTeaByClassName(@PathVariable("className") String className){
        return classService.findTeaByClassName(className);
    }

    @GetMapping("/findClassByClassName/{className}")
    public Result findClassByClassName(@PathVariable("className") String className){
        return classService.findClassByClassName(className);
    }

    @DeleteMapping("/deleteClassById/{id}")
    public Result deleteClassById(@PathVariable("id") Integer id){
        return classService.deleteClassById(id);
    }

    @PutMapping("/modifyClass/{oldClass}")
    public Result modifyClass(@RequestBody Class myClass,@PathVariable("oldClass")String oldClass){
        System.out.println(myClass);
        System.out.println(oldClass);
        return classService.modifyClass(myClass,oldClass);
    }

    @PutMapping("/modifyClass2")
    public Result modifyClass2(@RequestBody Class myClass){
        return classService.modifyClass2(myClass);
    }


    @GetMapping("/findClassById/{id}")
    public Result findClassById(@PathVariable("id") Integer id){
        return classService.findClassById(id);
    }

    @GetMapping("/findAllClasses")
    public Result findAllClasses(){
        return classService.findAllClasses();
    }
}


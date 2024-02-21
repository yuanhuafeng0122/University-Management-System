package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.EmployInfo;
import com.yuanhuafeng.employment.service.EmployInfoService;
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
@RequestMapping("/employInfo")
public class EmployInfoController {
    @Autowired
    private EmployInfoService employInfoService;


    @PostMapping("/addEmp/{account}")
    public Result addEmp(@RequestBody EmployInfo employInfo, @PathVariable("account") String account){
        System.out.println(employInfo.toString()+account);
        return employInfoService.addEmp(employInfo,account);
    }

    @GetMapping("/findEmpByNum/{number}")
    public Result findEmpByNum(@PathVariable("number") String number){
        return employInfoService.findEmpByNum(number);
    }

    @PutMapping("/updateEmp")
    public Result updateEmp(@RequestBody EmployInfo employInfo){
        return employInfoService.updateEmp(employInfo);
    }

    @GetMapping("findAllEmpByAcc/{account}")
    public Result findAllEmpByAcc(@PathVariable("account")String account){
        return employInfoService.findAllEmpByAcc(account);
    }

    //根据学生姓名查找就业信息
    @PostMapping("findEmpByName/{name}")
    public Result findEmpByName(@PathVariable("name") String name){
        return employInfoService.findEmpByName(name);
    }

    @GetMapping("/findEmpByNameAndPost/{name}")
    public Result findEmpByNameAndPost(@PathVariable("name") String name){
        return employInfoService.findEmpByNameAndPost(name);
    }

    //根据专业查找
    @GetMapping("findEmpByMajor/{major}")
    public Result findEmpByMajor(@PathVariable("major") String major){
        return employInfoService.findEmpByMajor(major);
    }

    //根据专业查找
    @GetMapping("/findEmpByMajorAndTeaId/{major}/{teaId}")
    public Result findEmpByMajorAndTeaId(@PathVariable("major") String major,@PathVariable("teaId") Integer teaId){
        return employInfoService.findEmpByMajorAndTeaId(major,teaId);
    }

    //根据班级查找
    @GetMapping("/findEmpByClassNameAndTeaId/{class}/{teaId}")
    public Result findEmpByClassNameAndTeaId(@PathVariable("class") String className,@PathVariable("teaId") Integer teaId){
        return employInfoService.findEmpByClassNameAndTeaId(className,teaId);
    }

    //根据班级查找
    @GetMapping("/findEmpByClassName/{class}")
    public Result findEmpByClassName(@PathVariable("class") String className){
        return employInfoService.findEmpByClassName(className);
    }

    //所有学生就业信息
    @GetMapping("/findAllEmp")
    public Result findAllEmp(){
        return employInfoService.findAllEmp();
    }

    //根据老师id查询学生就业信息
    @GetMapping("/findEmpByTeaId/{teaId}")
    public Result findEmpByTeaId(@PathVariable("teaId") Integer teaId){
        return employInfoService.findEmpByTeaId(teaId);
    }

    //根据省查找
    @GetMapping("/findEmpByProvinceAndTeaId/{province}/{teaId}")
    public Result findEmpByProvinceAndTeaId(@PathVariable("province") String province,@PathVariable("teaId") Integer teaId){
        return employInfoService.findEmpByProvinceAndTeaId(province,teaId);
    }

    @GetMapping("/findEmpByProvince/{province}")
    public Result findEmpByProvinceAndTeaId(@PathVariable("province") String province){
        return employInfoService.findEmpByProvince(province);
    }

    //根据市查找
    @GetMapping("/findEmpByCityAndTeaId/{city}/{teaId}")
    public Result findEmpByCityAndTeaId(@PathVariable("city") String  city,@PathVariable("teaId") Integer teaId){
        return employInfoService.findEmpByCityAndTeaId(city,teaId);
    }
    @GetMapping("/findEmpByCity/{city}")
    public Result findEmpByCity(@PathVariable("city") String  city){
        return employInfoService.findEmpByCity(city);
    }

    //更具区查找
    @GetMapping("/findEmpByAreaAndTeaId/{area}/{teaId}")
    public Result findEmpByAreaAndTeaId(@PathVariable("area") String  area,@PathVariable("teaId") Integer teaId){
        return employInfoService.findEmpByAreaAndTeaId(area,teaId);
    }

    @GetMapping("/findEmpByArea/{area}")
    public Result findEmpByAreaAndTeaId(@PathVariable("area") String  area){
        return employInfoService.findEmpByArea(area);
    }
}


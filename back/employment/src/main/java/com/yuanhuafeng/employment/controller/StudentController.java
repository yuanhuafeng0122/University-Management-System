package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Student;
import com.yuanhuafeng.employment.mapper.StudentMapper;
import com.yuanhuafeng.employment.mapper.UserMapper;
import com.yuanhuafeng.employment.service.StudentService;
import com.yuanhuafeng.employment.vo.params.ModifyStuNum;
import org.apache.ibatis.annotations.Update;
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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public Result addStudent(@RequestBody Student student){
        return studentService.insert(student);
    }

    //主页得到一个学生的信息
    @GetMapping("/getStudentInfo/{userAccount}")
    public Result findStuByAccount(@PathVariable("userAccount") String userAccount){
        return studentService.findStuByAccount(userAccount);
    }

    //得到学生的名字专业班级
    @GetMapping("/findNameByAccount/{account}")
    public Result findNameByAccount(@PathVariable("account") String account){
        return studentService.findNameByAccount(account);
    }

    //通过老师传过来的电话号码，先要查询出老师的姓名然后在查出姓名下的学生
    @GetMapping("/findAllStuByTeaAccount/{Account}")
    public Result findAllStuByTeaAccount(@PathVariable("Account") String account){
        return studentService.findAllStuByTeaAccount(account);
    }

    //管理员查找所有的学生
    @GetMapping("/findAllStu")
    public Result findAllStu(){
        return studentService.findAllStu();
    }

    //根据学生姓名查找学生
    @GetMapping("/findStuByName/{name}/{teaId}")
    public Result findStuByName(@PathVariable("name") String name,@PathVariable("teaId") Integer teaId){
        return studentService.findStuByName(name,teaId);
    }

    //根据学生的学号删除
    @DeleteMapping("/deleteStuByNum/{number}")
    public Result deleteStuByNum(@PathVariable("number") String number){
        return studentService.deleteStuByNum(number);
    }

    @DeleteMapping("/deleteStuById/{stuId}")
    public Result deleteStuById(@PathVariable("stuId") Integer stuId){
        return studentService.deleteStuById(stuId);
    }

    //修改学生信息
    @PutMapping("/modifyStu/{oldClassName}")
    public Result modifyStu(@RequestBody Student student,@PathVariable("oldClassName") String oldClassName){
        return studentService.modifyStu(student,oldClassName);
    }

    //修改学生的学号
    @PostMapping("/modifyStuNum")
    public Result modifyStuNum(@RequestBody ModifyStuNum modifyStuNum){
        return studentService.modifyStuNum(modifyStuNum);
    }

    //通过user的账号找到老师的Id在通过老师Id找到老师所对应的学生
    @GetMapping("/findStuByUserAccount/{account}")
    public Result findStuByUserAccount(@PathVariable("account") String account){
        return studentService.findStuByUserAccount(account);
    }

    //通过用户的账号找到唯一的学生
    @GetMapping("/findOneStuByNumber/{account}")
    public Result findOneStuByNumber(@PathVariable("account") String account){
        return studentService.findOneStuByNumber(account);
    }

    //修改学生信息升级版
    @PutMapping("/modiFyStu/{account}/{oldClassName}")
    public Result modiFyStu(@RequestBody Student student,@PathVariable("account")String account,@PathVariable("oldClassName") String oldClassName){
        System.out.println(student.toString()+"++++++"+account);
        return studentService.modiFyStu(student,account,oldClassName);
    }

    @GetMapping("/getAllTeaStu")
    public Result getAllTeaStu(){
        return studentService.getAllTeaStu();
    }

    @GetMapping("/findStuById/{id}")
    public Result findStuById(@PathVariable("id") Integer id){
        return studentService.findStuById(id);
    }

    @GetMapping("/findStuByTeaId/{teaId}")
    public Result findStuByTeaId(@PathVariable Integer teaId){
        return studentService.findStuByTeaId(teaId);
    }

    @GetMapping("/findStuByClassNameAndTeaId/{className}/{teaId}")
    public Result findStuByClassName(@PathVariable("className") String className,@PathVariable("teaId") Integer teaId){
        return studentService.findStuByClassNameAndTeaId(className,teaId);
    }

    @GetMapping("/findStuByMajorAndTeaId/{major}/{teaId}")
    public Result findStuByMajor(@PathVariable("major") String major,@PathVariable("teaId") Integer teaId){
        return studentService.findStuByMajorAndTeaId(major,teaId);
    }


    @GetMapping("/findStuByClassName/{className}")
    public Result findStuByClassName(@PathVariable("className") String className){
        return studentService.findStuByClassName(className);
    }

    @GetMapping("/findStuByMajor/{major}")
    public Result findStuByMajor(@PathVariable("major") String major){
        return studentService.findStuByMajor(major);
    }
}


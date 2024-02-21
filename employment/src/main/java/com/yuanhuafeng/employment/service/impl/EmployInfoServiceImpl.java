package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.ErrorCode;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Class;
import com.yuanhuafeng.employment.entity.EmployInfo;
import com.yuanhuafeng.employment.entity.Student;
import com.yuanhuafeng.employment.entity.Teacher;
import com.yuanhuafeng.employment.mapper.ClassMapper;
import com.yuanhuafeng.employment.mapper.EmployInfoMapper;
import com.yuanhuafeng.employment.mapper.StudentMapper;
import com.yuanhuafeng.employment.mapper.TeacherMapper;
import com.yuanhuafeng.employment.service.EmployInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-04
 */
@Service
public class EmployInfoServiceImpl extends ServiceImpl<EmployInfoMapper,EmployInfo> implements EmployInfoService{

    @Autowired(required = false)
    private EmployInfoMapper employInfoMapper;

    @Autowired(required = false)
    private TeacherMapper teacherMapper;

    @Autowired(required = false)
    private StudentMapper studentMapper;

    @Autowired(required = false)
    private ClassMapper classMapper;



    @Override
    public Result addEmp(EmployInfo employInfo,String account) {
        System.out.println(employInfo +"<<<<<<<<<<<< ");
        //添加完就业信息后要把学生标注为已经就业
        int row = employInfoMapper.insert(employInfo);
        if (row != 0){
            //设置为学生就业信息已经提交
            LambdaQueryWrapper<Student> studentQueryWrapper = new LambdaQueryWrapper<>();
            studentQueryWrapper.eq(Student::getNumber,account);
            Student student = studentMapper.selectOne(studentQueryWrapper);
            student.setIsWork(true);
            studentMapper.updateById(student);

            //然后找到学生所在的班级把填写人数加1，总人数减去填写的就是没有填写的
            LambdaQueryWrapper<Class> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Class::getClassName,student.getClassName());
            Class myClass = classMapper.selectOne(lambdaQueryWrapper);

            //填写人数
            BigInteger fillStu = myClass.getFillStu();
            //总人数
            BigInteger fullStu = myClass.getFullStu();

            int fillStu1 = fillStu.intValue() + 1;
            System.out.println(fillStu1+">>>>>>>>>>>>>>>>>>>>>>>填写人数");

            myClass.setFillStu(BigInteger.valueOf(fillStu1));

            int notFillStu = fullStu.intValue() - fillStu1;
            System.out.println(notFillStu+">>>>>>>>>>>>>>>>>>>>>>未填写人数");
            myClass.setNotFillStu(BigInteger.valueOf(notFillStu));

            //已注册未填写人数 = 注册人数-填写人数
            BigInteger trueStu = myClass.getTrueStu();
            int resNotFill = trueStu.intValue() - fillStu1;
            myClass.setResNotFill(BigInteger.valueOf(resNotFill));

            //更新班级数据
            classMapper.updateById(myClass);
            return Result.success();
        }else {
            return Result.fail(ErrorCode.EMP_ERROR.getCode(),ErrorCode.EMP_ERROR.getMsg());
        }

    }

    @Override
    public Result findEmpByNum(String number) {
        LambdaQueryWrapper<EmployInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EmployInfo::getNumber,number);
        queryWrapper.last("limit 1");
        EmployInfo employInfo = employInfoMapper.selectOne(queryWrapper);
        if (employInfo != null){
            return Result.success(employInfo);
        }else {
            return Result.fail();
        }
    }

    @Override
    public Result updateEmp(EmployInfo employInfo) {
        employInfoMapper.updateById(employInfo);
        return Result.success();
    }

    @Override
    public Result findAllEmpByAcc(String account) {

        //首先要通过手机号先找到辅导员的姓名在通过姓名去找学生的就业信息
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getPhone,account);
        queryWrapper.last("limit 1");
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        String name = teacher.getName();

        QueryWrapper<EmployInfo> employInfoQueryWrapper = new QueryWrapper<>();
        employInfoQueryWrapper.eq("teacher_name",name);
        List<EmployInfo> list = employInfoMapper.selectList(employInfoQueryWrapper);
        return Result.success(list);
    }

    @Override
    public Result findEmpByName(String name) {

        LambdaQueryWrapper<EmployInfo> employInfoQueryWrapper = new LambdaQueryWrapper<>();
        employInfoQueryWrapper.like(EmployInfo::getStudentName,name);
        List<EmployInfo> list = employInfoMapper.selectList(employInfoQueryWrapper);

        LambdaQueryWrapper<EmployInfo> employInfoQueryWrapper1 = new LambdaQueryWrapper<>();
        employInfoQueryWrapper1.like(EmployInfo::getNumber,name);
        List<EmployInfo> list1 = employInfoMapper.selectList(employInfoQueryWrapper1);
        list.addAll(list1);


        System.out.println(list);

        if (list.size() != 0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByMajor(String major) {
        LambdaQueryWrapper<EmployInfo> employInfoQueryWrapper = new LambdaQueryWrapper<>();
        employInfoQueryWrapper.like(EmployInfo::getMajor,major);
        List<EmployInfo> list = employInfoMapper.selectList(employInfoQueryWrapper);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findAllEmp() {
        LambdaQueryWrapper<EmployInfo> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        List<EmployInfo> list = employInfoMapper.selectList(lambdaQueryWrapper);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByTeaId(Integer teaId) {
        List<EmployInfo> list = employInfoMapper.findEmpByTeaId(teaId);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByMajorAndTeaId(String major, Integer teaId) {
        List<EmployInfo> list = employInfoMapper.findEmpByMajorAndTeaId(major,teaId);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByClassNameAndTeaId(String className, Integer teaId) {
        List<EmployInfo> list = employInfoMapper.findEmpByClassNameAndTeaId(className,teaId);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByClassName(String className) {
        LambdaQueryWrapper<EmployInfo> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(EmployInfo::getClasses,className);
        List<EmployInfo> list = employInfoMapper.selectList(lambdaQueryWrapper);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByNameAndPost(String name) {
        LambdaQueryWrapper<EmployInfo> employInfoQueryWrapper = new LambdaQueryWrapper<>();
        employInfoQueryWrapper.like(EmployInfo::getStudentName,name);
        List<EmployInfo> list = employInfoMapper.selectList(employInfoQueryWrapper);

        LambdaQueryWrapper<EmployInfo> employInfoQueryWrapper1 = new LambdaQueryWrapper<>();
        employInfoQueryWrapper1.like(EmployInfo::getNumber,name);
        List<EmployInfo> list1 = employInfoMapper.selectList(employInfoQueryWrapper1);
        list.addAll(list1);

        LambdaQueryWrapper<EmployInfo> employInfoQueryWrapper2 = new LambdaQueryWrapper<>();
        employInfoQueryWrapper2.like(EmployInfo::getPost,name);
        List<EmployInfo> list2 = employInfoMapper.selectList(employInfoQueryWrapper1);
        list.addAll(list2);

        System.out.println(list);

        if (list.size() != 0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByProvinceAndTeaId(String province, Integer teaId) {

        List<EmployInfo> list = employInfoMapper.findEmpByProvinceAndTeaId(province,teaId);

        if (list.size() != 0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByProvince(String province) {
        LambdaQueryWrapper<EmployInfo> employInfoQueryWrapper = new LambdaQueryWrapper<>();
        employInfoQueryWrapper.like(EmployInfo::getProvince,province);
        List<EmployInfo> list = employInfoMapper.selectList(employInfoQueryWrapper);

        if (list.size() != 0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByCityAndTeaId(String city, Integer teaId) {
        List<EmployInfo> list = employInfoMapper.findEmpByCityAndTeaId(city,teaId);

        if (list.size() != 0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByCity(String city) {
        LambdaQueryWrapper<EmployInfo> employInfoQueryWrapper = new LambdaQueryWrapper<>();
        employInfoQueryWrapper.like(EmployInfo::getCity,city);
        List<EmployInfo> list = employInfoMapper.selectList(employInfoQueryWrapper);

        if (list.size() != 0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByAreaAndTeaId(String area, Integer teaId) {
        List<EmployInfo> list = employInfoMapper.findEmpByAreaAndTeaId(area,teaId);

        if (list.size() != 0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findEmpByArea(String area) {
        LambdaQueryWrapper<EmployInfo> employInfoQueryWrapper = new LambdaQueryWrapper<>();
        employInfoQueryWrapper.like(EmployInfo::getArea,area);
        List<EmployInfo> list = employInfoMapper.selectList(employInfoQueryWrapper);

        if (list.size() != 0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.EMPNULL_ERROR.getCode(), ErrorCode.EMPNULL_ERROR.getMsg());
        }
    }


}

package com.yuanhuafeng.employment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.LambadaTools;
import com.yuanhuafeng.employment.entity.*;
import com.yuanhuafeng.employment.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class EmploymentApplicationTest {

    @Autowired(required = false)
    private CollegeMapper collegeMapper;
    @Autowired(required = false)
    private MajorMapper majorMapper;

    @Autowired(required = false)
    private CitiesMapper citiesMapper;

    @Autowired(required = false)
    private TeacherMapper teacherMapper;
    @Autowired(required = false)
    private EmployInfoMapper employInfoMapper;
    @Autowired(required = false)
    private ProvincesMapper provincesMapper;

    @Autowired(required = false)
    private AreasMapper areasMapper;
    /**
     * 查询所有数据
     * UserMapper 中的 selectList() 方法的参数
     * 为 MP 内置的条件封装器 Wrapper，所以不填写就是无任何条件（条件构造器）
     */
    @Test
    public void selectList() {
        //查询所有的学院的字段
//        List<College> list = collegeMapper.selectList(null);
//        list.forEach(System.out::println);

        //查询所有学院不包括描述
//        QueryWrapper<College> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.select("id", "name");
//        List<College> collegeList = collegeMapper.selectList(userQueryWrapper);
//        collegeList.forEach(System.out::println);

//        //根据学院的Id查询专业
//        LambdaQueryWrapper<Major> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Major::getCollegeId,1);
//        List<Major> list = this.majorMapper.selectList(lambdaQueryWrapper);
//        list.forEach(System.out::println);


//        //首先要通过手机号先找到辅导员的姓名在通过姓名去找学生的就业信息
//        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Teacher::getPhone,"17674189259");
//        queryWrapper.last("limit 1");
//        Teacher teacher = teacherMapper.selectOne(queryWrapper);
//        String name = teacher.getName();
//
//        QueryWrapper<EmployInfo> employInfoQueryWrapper = new QueryWrapper<>();
//        employInfoQueryWrapper.eq("teacher_name",name);
//        List<EmployInfo> list = employInfoMapper.selectList(employInfoQueryWrapper);
//        list.forEach(System.out::println);

//        QueryWrapper<Provinces> provincesQueryWrapper = new QueryWrapper<>();
//        LambdaQueryWrapper<Cities> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        provincesQueryWrapper.select("provinceid", "province");
//        List<Provinces> provincesList = provincesMapper.selectList(provincesQueryWrapper);
//
//        System.out.println(provincesList);
//        QueryWrapper<Cities> queryWrapper = new QueryWrapper<>();
//        List<Cities> list = citiesMapper.selectList(queryWrapper);
//        System.out.println(list);

//        int id = 6;
////        Teacher teacher = teacherMapper.findTea(id);
//        System.out.println(id);


//        QueryWrapper<Provinces> provincesQueryWrapper = new QueryWrapper<>();
//        provincesQueryWrapper.select("provinceid", "province");
//        LinkedList<Provinces> provincesList = (LinkedList<Provinces>) provincesMapper.selectList(provincesQueryWrapper);
//
//        QueryWrapper<Cities> queryWrapper = new QueryWrapper<>();
//        List<Cities> citiesList = citiesMapper.selectList(queryWrapper);
//
//        QueryWrapper<Areas> queryWrapper1 = new QueryWrapper<>();
//        List<Areas> areasList = areasMapper.selectList(queryWrapper1);
//
//        provincesList.forEach(LambadaTools.forEachWithIndex((item,index) -> {
//            provincesList.push();
//            System.out.println(index +":"+item);
//        }));


    }




    @Test
    void contextLoads() {
    }

}

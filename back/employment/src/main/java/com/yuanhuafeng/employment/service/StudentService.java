package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuanhuafeng.employment.vo.params.ModifyStuNum;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-03
 */
public interface StudentService extends IService<Student> {

    Result insert(Student student);

    Result findStuByAccount(String userAccount);

    Result findNameByAccount(String account);

    Result findAllStuByTeaAccount(String account);

    Result findAllStu();

    Result findStuByName(String name,Integer teaId);

    Result deleteStuByNum(String number);

    Result modifyStu(Student student,String oldClassName);

    Result modifyStuNum(ModifyStuNum modifyStuNum);

    Result findStuByUserAccount(String account);

    Result findOneStuByNumber(String account);

    Result modiFyStu(Student student,String account,String oldClassName);

    Result getAllTeaStu();

    Result findStuById(Integer id);

    Result findStuByTeaId(Integer teaId);

    Result deleteStuById(Integer stuId);

    Result findStuByClassNameAndTeaId(String className,Integer teaId);

    Result findStuByMajorAndTeaId(String major,Integer teaId);

    Result findStuByClassName(String className);

    Result findStuByMajor(String major);
}

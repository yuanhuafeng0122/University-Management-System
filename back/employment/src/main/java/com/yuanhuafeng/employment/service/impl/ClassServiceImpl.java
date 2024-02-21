package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuanhuafeng.employment.config.ErrorCode;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.*;
import com.yuanhuafeng.employment.entity.Class;
import com.yuanhuafeng.employment.mapper.ClassMapper;
import com.yuanhuafeng.employment.mapper.StudentMapper;
import com.yuanhuafeng.employment.mapper.TeacherMapper;
import com.yuanhuafeng.employment.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuanhuafeng.employment.vo.ClassTeaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-12-05
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Autowired(required = false)
    private ClassMapper classMapper;
    @Autowired(required = false)
    private TeacherMapper teacherMapper;
    @Autowired(required = false)
    private StudentMapper studentMapper;

    @Override
    public Result addClass(Class myClass) {


        myClass.setNotTrueStu(myClass.getFullStu());

        String teaId = myClass.getTeaId();
        myClass.setNotFillStu(myClass.getFullStu());
        int row = classMapper.insert(myClass);
        if (row != 0) {
            LambdaQueryWrapper<Class> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Class::getTeaId, teaId);
            List<Class> list = classMapper.selectList(lambdaQueryWrapper);
            return Result.success(list);
//            findClassesByTeaId(teaId);
        }
        return Result.fail(ErrorCode.ADDCLASS_ERROR.getCode(), ErrorCode.ADDCLASS_ERROR.getMsg());
    }

    @Override
    public Result findClassesByTeaId(String teaId) {

        LambdaQueryWrapper<Class> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Class::getTeaId, teaId);
        List<Class> list = classMapper.selectList(lambdaQueryWrapper);
        if (list.size() != 0) {
            return Result.success(list);
        } else {
            return Result.fail(ErrorCode.FINDCLASSES_ERROR.getCode(), ErrorCode.FINDCLASSES_ERROR.getMsg());
        }

    }

    @Override
    public Result findClassByMajor(String majorName) {
        LambdaQueryWrapper<Class> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Class::getClassMajor, majorName);
        List<Class> list = classMapper.selectList(lambdaQueryWrapper);
        if (list.size() != 0) {
            return Result.success(list);
        } else {
            return Result.fail(ErrorCode.FINDCLASSES_ERROR.getCode(), ErrorCode.FINDCLASSES_ERROR.getMsg());
        }
    }

    @Override
    public Result findTeaByClassName(String className) {
        LambdaQueryWrapper<Class> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Class::getClassName, className);
        Class myClass = classMapper.selectOne(lambdaQueryWrapper);
        if (myClass != null) {
            String teaId = myClass.getTeaId();
            Teacher teacher = teacherMapper.selectById(teaId);
            return Result.success(teacher);
        } else {
            return Result.fail(ErrorCode.FINDTEA_ERROR.getCode(), ErrorCode.FINDTEA_ERROR.getMsg());
        }

    }

    @Override
    public Result findClassByClassName(String className) {
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getClassName, className).last("limit 1");
        //确保只能查询一条
        Class myClass = classMapper.selectOne(queryWrapper);
        if (myClass != null) {
            System.out.println("班级已经存在");
            return Result.fail(ErrorCode.CLASS_EXIST.getCode(), ErrorCode.CLASS_EXIST.getMsg());
        } else {
            return Result.success();
        }
    }

    @Override
    public Result deleteClassById(Integer id) {
        int row = classMapper.deleteById(id);
        System.out.println(row + "<<<<<<<<<<<<<<<>>>>>>>>");
        if (row != 0) {
            return Result.success();
        } else {
            return Result.fail(ErrorCode.DELETECLA_ERROR.getCode(), ErrorCode.DELETECLA_ERROR.getMsg());
        }

    }

    @Override
    public Result modifyClass(Class myClass, String oldClass) {


        //还要修改学生的信息
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Student::getClassName, oldClass);
        Student student = new Student();
        student.setClassName(myClass.getClassName());
        Integer rows = studentMapper.update(student, lambdaQueryWrapper);


//
//        for (int i = 0; i<list.size() ; i++){
//            list.get(i).setClassName(myClass.getClassName());
//        }


        BigInteger fullStu = myClass.getFullStu();
        BigInteger tureStu = myClass.getTrueStu();

        int notTrueStu = fullStu.intValue() - tureStu.intValue();
        myClass.setNotTrueStu(BigInteger.valueOf(notTrueStu));

        BigInteger fillStu = myClass.getFillStu();
        int notFillStu = fullStu.intValue() - fillStu.intValue();
        myClass.setNotFillStu(BigInteger.valueOf(notFillStu));


        int row = classMapper.updateById(myClass);
        if (row != 1) {
            return Result.fail(ErrorCode.MODIFYCLA_ERROR.getCode(), ErrorCode.MODIFYCLA_ERROR.getMsg());
        } else {
            return Result.success();
        }
    }

    @Override
    public Result findClassById(Integer id) {
        Class myClass = classMapper.selectById(id);
        if (myClass != null) {
            return Result.success(myClass);
        } else {
            return Result.fail(ErrorCode.FINDCLASSES_ERROR.getCode(), ErrorCode.FINDCLASSES_ERROR.getMsg());
        }

    }

    @Override
    public Result findAllClasses() {
        List<ClassTeaVo> list = classMapper.findAllClasses();
        if (list.size() != 0) {
            return Result.success(list);
        } else {
            return Result.fail(ErrorCode.FINDCLASSES_ERROR.getCode(), ErrorCode.FINDCLASSES_ERROR.getMsg());
        }


    }

    @Override
    public Result modifyClass2(Class myClass) {
        int row = classMapper.updateById(myClass);
        if (row != 0) {
            Class mClass = classMapper.selectById(myClass.getId());
            if (mClass != null) {
                return Result.success(mClass);
            } else {
                return Result.fail(ErrorCode.UPDATA_ERROR.getCode(), ErrorCode.UPDATA_ERROR.getMsg());
            }
        } else {
            return Result.fail(ErrorCode.UPDATA_ERROR.getCode(), ErrorCode.UPDATA_ERROR.getMsg());
        }


    }
}

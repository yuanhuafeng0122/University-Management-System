package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.ErrorCode;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Major;
import com.yuanhuafeng.employment.entity.Student;
import com.yuanhuafeng.employment.entity.Teacher;
import com.yuanhuafeng.employment.entity.User;
import com.yuanhuafeng.employment.mapper.TeacherMapper;
import com.yuanhuafeng.employment.mapper.UserMapper;
import com.yuanhuafeng.employment.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuanhuafeng.employment.vo.TeaAndCla;
import com.yuanhuafeng.employment.vo.params.TeaClaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-04
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired(required = false)
    private TeacherMapper teacherMapper;
    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public Result findAllTeaByCollegeName(String name) {
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teacher::getCollegeName,name);
        List<Teacher> list = this.list(lambdaQueryWrapper);
        return Result.success(list);

    }

    @Override
    public Result findAllTea() {
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.notIn(Teacher::getId,11);
        List<Teacher> list = teacherMapper.selectList(lambdaQueryWrapper);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.TEANULL_ERROR.getCode(), ErrorCode.TEANULL_ERROR.getMsg());
        }
    }

    @Override
    public Result delTeaByAccount(String phone) {
        //更具老师的电话号码删除，然后也要删除用户
        Map<String,Object> teaMap = new HashMap<>();
        teaMap.put("phone",phone);
        int teaRow = teacherMapper.deleteByMap(teaMap);

        Map<String,Object> userMap = new HashMap<>();
        userMap.put("account",phone);
        int userRow = userMapper.deleteByMap(userMap);

        if (teaRow == 1 && userRow == 1 ){
            return Result.success();
        }else {
            return Result.fail(ErrorCode.DEL_FAIL.getCode(), ErrorCode.DEL_FAIL.getMsg());
        }

    }

    @Override
    public Result modifyTea(Teacher teacher) {
        int row =  teacherMapper.updateById(teacher);
        //修改完自己的信息后需要到用户表查询出来也修改
        if (row == 1){
            return Result.success(teacher);
        }else {
            return Result.fail(ErrorCode.MODIFYTEA_ERROR.getCode(),ErrorCode.MODIFYTEA_ERROR.getMsg());
        }
    }

    @Override
    public Result findTeaByPhone(String phone) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getPhone,phone);
        queryWrapper.last("limit 1");
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        if (teacher != null){
            System.out.println("<<<<<<<<<<<<<>.....>");
            return Result.success(teacher);
        }else {
            return Result.fail(ErrorCode.TEAINFO_ERROR.getCode(),ErrorCode.TEAINFO_ERROR.getMsg());
        }
    }

    @Override
    public Result findTeaById(Integer id) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getId,id);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);

        System.out.println(teacher.toString()+"11111");
        if (teacher!=null){
            return Result.success(teacher);
        }else {
            return Result.fail(ErrorCode.TEANOTFIND_ERROR.getCode(),ErrorCode.TEANULL_ERROR.getMsg());
        }
    }

    @Override
    public Result modifyTeaByPhone(Teacher teacher, String phone) {
        int row =  teacherMapper.updateById(teacher);
        //修改完自己的信息后需要到用户表查询出来也修改
        System.out.println(row);
        if (row == 1){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getAccount,phone);
            User user = userMapper.selectOne(queryWrapper);
            if (user != null){
                user.setAccount(teacher.getPhone());
                user.setPhone(teacher.getPhone());
                userMapper.updateById(user);
            }else {
                Result.fail(ErrorCode.NOTFINDEUSER.getCode(), ErrorCode.NOTFINDEUSER.getMsg());
            }
            System.out.println(user);
            return Result.success(teacher,user);
        }else {
            return Result.fail(ErrorCode.MODIFYTEA_ERROR.getCode(),ErrorCode.MODIFYTEA_ERROR.getMsg());
        }
    }

    @Override
    public Result findTeaAndClass(TeaClaId teaClaId) {

        return null;
//        return Result.success(teaAndCla);
    }
}

package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuanhuafeng.employment.vo.params.TeaClaId;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-04
 */
public interface TeacherService extends IService<Teacher> {

    Result findAllTeaByCollegeName(String name);

    Result findAllTea();

    Result delTeaByAccount(String phone);

    Result modifyTea(Teacher teacher);

    Result findTeaByPhone(String phone);

    Result findTeaById(Integer id);

    Result modifyTeaByPhone(Teacher teacher, String phone);

    Result findTeaAndClass(TeaClaId teaClaId);
}

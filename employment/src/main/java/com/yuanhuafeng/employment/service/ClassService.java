package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-12-05
 */
public interface ClassService extends IService<Class> {

    Result addClass(Class myClass);

    Result findClassesByTeaId(String teaId);

    Result findClassByMajor(String majorName);

    Result findTeaByClassName(String className);

    Result findClassByClassName(String className);

    Result deleteClassById(Integer id);

    Result modifyClass(Class myClass,String oldClass);

    Result findClassById(Integer id);

    Result findAllClasses();

    Result modifyClass2(Class myClass);
}

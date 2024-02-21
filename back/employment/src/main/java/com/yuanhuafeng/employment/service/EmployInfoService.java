package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.EmployInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-04
 */
public interface EmployInfoService  {

    Result addEmp(EmployInfo employInfo,String account);

    Result findEmpByNum(String number);

    Result updateEmp(EmployInfo employInfo);

    Result findAllEmpByAcc(String account);

    Result findEmpByName(String name);

    Result findEmpByMajor(String major);

    Result findAllEmp();

    Result findEmpByTeaId(Integer teaId);

    Result findEmpByMajorAndTeaId(String major, Integer teaId);

    Result findEmpByClassNameAndTeaId(String className, Integer teaId);

    Result findEmpByClassName(String className);

    Result findEmpByNameAndPost(String name);

    Result findEmpByProvinceAndTeaId(String province, Integer teaId);

    Result findEmpByProvince(String province);

    Result findEmpByCityAndTeaId(String city, Integer teaId);

    Result findEmpByCity(String city);

    Result findEmpByAreaAndTeaId(String area, Integer teaId);

    Result findEmpByArea(String area);
}

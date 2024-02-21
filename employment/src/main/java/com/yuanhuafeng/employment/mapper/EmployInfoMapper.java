package com.yuanhuafeng.employment.mapper;

import com.yuanhuafeng.employment.entity.EmployInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-04
 */

public interface EmployInfoMapper extends BaseMapper<EmployInfo> {

    List<EmployInfo> findEmpByTeaId(Integer teaId);

    List<EmployInfo> findEmpByMajorAndTeaId(String major, Integer teaId);

    List<EmployInfo> findEmpByClassNameAndTeaId(String className, Integer teaId);

    List<EmployInfo> findEmpByProvinceAndTeaId(String province, Integer teaId);

    List<EmployInfo> findEmpByCityAndTeaId(String city, Integer teaId);

    List<EmployInfo> findEmpByAreaAndTeaId(String area, Integer teaId);
}

package com.yuanhuafeng.employment.mapper;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuanhuafeng.employment.vo.TeaStuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-03
 */

public interface StudentMapper extends BaseMapper<Student> {

    //改完班级名后批量跟新学生
    int updateStuClassName(@Param(value = "list")List<Student> list);

    List<TeaStuVo> getAllTeaStu();

    TeaStuVo findStuById(Integer id);
}

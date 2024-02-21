package com.yuanhuafeng.employment.mapper;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuanhuafeng.employment.vo.TeaAndCla;
import com.yuanhuafeng.employment.vo.params.TeaClaId;
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
 * @since 2021-11-04
 */

public interface TeacherMapper extends BaseMapper<Teacher> {

}

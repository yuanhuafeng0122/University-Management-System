package com.yuanhuafeng.employment.mapper;

import com.yuanhuafeng.employment.entity.Class;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuanhuafeng.employment.vo.ClassTeaVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-12-05
 */

public interface ClassMapper extends BaseMapper<Class> {

    List<ClassTeaVo> findAllClasses();
}

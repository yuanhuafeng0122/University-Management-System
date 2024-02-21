package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.ErrorCode;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.College;
import com.yuanhuafeng.employment.mapper.CollegeMapper;
import com.yuanhuafeng.employment.service.CollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-03
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {
    @Autowired(required = false)
    private CollegeMapper collegeMapper;

    @Override
    public Result findAllCollege() {
        LambdaQueryWrapper<College> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.select(College::getId, College::getName);
        List<College> collegeList = collegeMapper.selectList(QueryWrapper);
        if (collegeList.size()!=0){
            return Result.success(collegeList);
        }else {
            return Result.fail(ErrorCode.FINDCOLLEGE_ERROR.getCode(),ErrorCode.FINDCOLLEGE_ERROR.getMsg());
        }

    }

    @Override
    public Result findCollegeByMajor(Integer id) {
        College college = collegeMapper.selectById(id);
        if (college!=null){
            return Result.success(college);
        }else {
            return Result.fail(ErrorCode.FINDCOLLEGE_ERROR.getCode(),ErrorCode.FINDCOLLEGE_ERROR.getMsg());
        }

    }
}

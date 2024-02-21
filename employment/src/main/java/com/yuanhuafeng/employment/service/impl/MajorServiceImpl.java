package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.ErrorCode;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.College;
import com.yuanhuafeng.employment.entity.Major;
import com.yuanhuafeng.employment.mapper.CollegeMapper;
import com.yuanhuafeng.employment.mapper.MajorMapper;
import com.yuanhuafeng.employment.service.MajorService;
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
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public Result findMajorByCollege(Integer collegeId) {
        LambdaQueryWrapper<Major> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Major::getCollegeId,collegeId);
        List<Major> list = this.list(lambdaQueryWrapper);
        if (list.size()==0){
            return Result.fail(ErrorCode.NOTFINDMAJOR_ERROR.getCode(),ErrorCode.NOTFINDMAJOR_ERROR.getMsg());
        }
        return Result.success(list);
    }

    @Override
    public Result findMajorByCollegeName(String collegeName) {

        //先找到学院Id，通过学院名称找Id
        LambdaQueryWrapper<College> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(College::getName,collegeName);
        College college = collegeMapper.selectOne(lambdaQueryWrapper);

        LambdaQueryWrapper<Major> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.eq(Major::getCollegeId,college.getId());
        List<Major> list = this.list(QueryWrapper);
        if (list.size()==0){
            return Result.fail(ErrorCode.NOTFINDMAJOR_ERROR.getCode(),ErrorCode.NOTFINDMAJOR_ERROR.getMsg());
        }
        return Result.success(list);
    }

    @Override
    public Result findAllMajor() {
        LambdaQueryWrapper<Major> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Major> list = majorMapper.selectList(lambdaQueryWrapper);
        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.NOTFINDMAJOR_ERROR.getCode(),ErrorCode.NOTFINDMAJOR_ERROR.getMsg());
        }

    }
}

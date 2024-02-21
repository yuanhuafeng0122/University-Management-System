package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Areas;
import com.yuanhuafeng.employment.entity.Cities;
import com.yuanhuafeng.employment.mapper.AreasMapper;
import com.yuanhuafeng.employment.service.AreasService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 区县信息表 服务实现类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
@Service
public class AreasServiceImpl extends ServiceImpl<AreasMapper, Areas> implements AreasService {
    @Autowired
    private AreasMapper areasMapper;

    @Override
    public Result findAllAre(String cityId) {
        LambdaQueryWrapper<Areas> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Areas::getCityId,cityId);
        List<Areas> list = this.list(lambdaQueryWrapper);
        return Result.success(list);
    }

    @Override
    public Result findAllArea() {
        QueryWrapper<Areas> queryWrapper = new QueryWrapper<>();
        List<Areas> list = areasMapper.selectList(queryWrapper);
        return Result.success(list);

    }
}

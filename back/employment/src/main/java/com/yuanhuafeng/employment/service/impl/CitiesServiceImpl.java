package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Cities;
import com.yuanhuafeng.employment.entity.Major;
import com.yuanhuafeng.employment.entity.Provinces;
import com.yuanhuafeng.employment.mapper.CitiesMapper;
import com.yuanhuafeng.employment.service.CitiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 城市信息表 服务实现类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
@Service
public class CitiesServiceImpl extends ServiceImpl<CitiesMapper, Cities> implements CitiesService {

    @Autowired
    private CitiesMapper citiesMapper;

    @Override
    public Result findAllCity(String provinceId) {
        LambdaQueryWrapper<Cities> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Cities::getProvinceId,provinceId);
        List<Cities> list = this.list(lambdaQueryWrapper);
        return Result.success(list);
    }

    @Override
    public Result findAllCity1() {
        QueryWrapper<Cities> queryWrapper = new QueryWrapper<>();
        List<Cities> list = citiesMapper.selectList(queryWrapper);
        return Result.success(list);
    }
}

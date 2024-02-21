package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Cities;
import com.yuanhuafeng.employment.entity.College;
import com.yuanhuafeng.employment.entity.Provinces;
import com.yuanhuafeng.employment.mapper.AreasMapper;
import com.yuanhuafeng.employment.mapper.CitiesMapper;
import com.yuanhuafeng.employment.mapper.ProvincesMapper;
import com.yuanhuafeng.employment.service.ProvincesService;
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
 * @since 2021-11-05
 */
@Service
public class ProvincesServiceImpl extends ServiceImpl<ProvincesMapper, Provinces> implements ProvincesService {

    @Autowired
    private ProvincesMapper provincesMapper;
    @Autowired
    private CitiesMapper citiesMapper;
    @Autowired
    private AreasMapper areasMapper;


    @Override
    public Result findAllPro() {
        QueryWrapper<Provinces> provincesQueryWrapper = new QueryWrapper<>();
        provincesQueryWrapper.select("provinceid", "province");
        List<Provinces> provincesList = provincesMapper.selectList(provincesQueryWrapper);
        return Result.success(provincesList);
    }

    @Override
    public Result findAllArea() {
        QueryWrapper<Provinces> provincesQueryWrapper = new QueryWrapper<>();
        provincesQueryWrapper.select("provinceid", "province");
        List<Provinces> provincesList = provincesMapper.selectList(provincesQueryWrapper);


        return null;
    }
}

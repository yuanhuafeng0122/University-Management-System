package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Cities;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 城市信息表 服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
public interface CitiesService extends IService<Cities> {

    Result findAllCity(String provinceId);

    Result findAllCity1();
}

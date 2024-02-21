package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Areas;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 区县信息表 服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */

public interface AreasService extends IService<Areas> {

    Result findAllAre(String cityId);

    Result findAllArea();
}

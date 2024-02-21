package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Major;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-03
 */
public interface MajorService extends IService<Major> {

    Result findMajorByCollege(Integer collegeId);

    Result findMajorByCollegeName(String collegeName);

    Result findAllMajor();
}

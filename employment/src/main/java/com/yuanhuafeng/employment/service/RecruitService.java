package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Recruit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-05
 */
public interface RecruitService extends IService<Recruit> {

    Result addRecruit(Recruit recruit);

    Result findAllRec();

    Result findRecByPost(String post);

    Result deleteRecById(Integer id);

    Result modifyRec(Recruit recruit);

    Result findRecById(Integer id);

    Result findRecByPubId(Integer pubId);
}

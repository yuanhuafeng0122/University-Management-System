package com.yuanhuafeng.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.ErrorCode;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Recruit;
import com.yuanhuafeng.employment.mapper.RecruitMapper;
import com.yuanhuafeng.employment.service.RecruitService;
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
public class RecruitServiceImpl extends ServiceImpl<RecruitMapper, Recruit> implements RecruitService {

    @Autowired(required = false)
    private RecruitMapper recruitMapper;

    @Override
    public Result addRecruit(Recruit recruit) {
        System.out.println(recruit+"11111");
        int row =  recruitMapper.insert(recruit);

        if (row != 0){
            return Result.success();
        }else {
            return Result.fail(ErrorCode.ADDREC_ERROR.getCode(),ErrorCode.ADDREC_ERROR.getMsg());
        }


    }

    @Override
    public Result findAllRec() {



        LambdaQueryWrapper<Recruit> queryWrapper = new LambdaQueryWrapper<>();
//        List<Recruit> list = recruitMapper.selectList(queryWrapper);
        queryWrapper.orderByAsc(Recruit::getReleaseTime);
        List<Recruit> list = recruitMapper.selectList(queryWrapper);
        System.out.println(list);
        return Result.success(list);
    }

    @Override
    public Result findRecByPost(String post) {
        QueryWrapper<Recruit> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("post",post);
        List<Recruit> list = recruitMapper.selectList(queryWrapper);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.RECNULL_ERROR.getCode(), ErrorCode.RECNULL_ERROR.getMsg());
        }

    }

    @Override
    public Result deleteRecById(Integer id) {
        int row = recruitMapper.deleteById(id);
        if (row == 1){
            return Result.success();
        }else {
            return Result.fail(ErrorCode.DEL_FAIL.getCode(),ErrorCode.DEL_FAIL.getMsg());
        }
    }

    @Override
    public Result modifyRec(Recruit recruit) {
        int row =  recruitMapper.updateById(recruit);
        if (row == 1){
            return Result.success();
        }else {
            return Result.fail(ErrorCode.MODIFYREC_ERROR.getCode(),ErrorCode.MODIFYREC_ERROR.getMsg());
        }

    }

    @Override
    public Result findRecById(Integer id) {
        Recruit recruit = recruitMapper.selectById(id);
        if (recruit !=null ){
            return Result.success(recruit);
        }else {
            return Result.fail(ErrorCode.ERROR.getCode(),ErrorCode.ERROR.getMsg());
        }

    }

    @Override
    public Result findRecByPubId(Integer pubId) {
        QueryWrapper<Recruit> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("pub_id",pubId);
        List<Recruit> list = recruitMapper.selectList(queryWrapper);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.NOTFINDREC_ERROR.getCode(), ErrorCode.NOTFINDREC_ERROR.getMsg());
        }

    }
}

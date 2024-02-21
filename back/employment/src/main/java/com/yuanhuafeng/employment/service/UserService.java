package com.yuanhuafeng.employment.service;

import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuanhuafeng.employment.vo.params.LoginParam;
import com.yuanhuafeng.employment.vo.UserAndStudent;
import com.yuanhuafeng.employment.vo.UserAndTeacher;
import com.yuanhuafeng.employment.vo.params.ModifyPasswordParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-02
 */
public interface UserService extends IService<User> {

    Result login(LoginParam loginParam);

    User findUser(String account, String password, String roleId);

    Result findUserByToken(String token);

    User checkToken(String token);

    Result logout();

    Result regUserAndStu(UserAndStudent userAndStudent);

    User findUserByAccount(String account);

    Result regUserAndTea(UserAndTeacher userAndTeacher);

    Result modifyPassword(ModifyPasswordParam modifyPasswordParam);

    Result isAccount(String account);
}

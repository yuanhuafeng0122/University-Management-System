package com.yuanhuafeng.employment.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yuanhuafeng.employment.config.ErrorCode;
import com.yuanhuafeng.employment.config.JWTUtils;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.Class;
import com.yuanhuafeng.employment.entity.User;
import com.yuanhuafeng.employment.mapper.ClassMapper;
import com.yuanhuafeng.employment.mapper.StudentMapper;
import com.yuanhuafeng.employment.mapper.TeacherMapper;
import com.yuanhuafeng.employment.mapper.UserMapper;
import com.yuanhuafeng.employment.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuanhuafeng.employment.vo.params.LoginParam;
import com.yuanhuafeng.employment.vo.UserAndStudent;
import com.yuanhuafeng.employment.vo.UserAndTeacher;
import com.yuanhuafeng.employment.vo.params.ModifyPasswordParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final String slat = "yuanhuafeng";

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {
        /**
         *1.检查参数是否合法
         *2.根据用户和密码去user表中查询是否存在
         * 如果不存在登录失败
         * 如果存在使用JWT生成token返回前端
         * token 放入redis当中，redis token: user信息 设置过期时间
         * （登录认证的时候先认证token是否合法去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String roleId = String.valueOf(loginParam.getRoleId());
        System.out.println(account+password+roleId);
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(roleId)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password+slat);
        User user = userService.findUser(account,password,roleId);
        if (user == null){
            return Result.fail(ErrorCode.ACCOUNT_ERROR.getCode(),ErrorCode.ACCOUNT_ERROR.getMsg());
        }
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        return Result.success(user);
    }

    @Override
    public User findUser(String account, String password, String roleId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account);
        queryWrapper.eq(User::getPassword,password);
        queryWrapper.eq(User::getRoleId,roleId);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token 合法性校验
         * 是否   为空 解析是否成功redis是否存在
         * 如果校验失败，返回错误
         * 如果成功返回对应的结果 LoginUserVo
         * @author YUAN
         * @date 2021-10-25 10:43
         * @param token
         * @return Result
         */
        User user = userService.checkToken(token);
        if (user == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        return Result.success(user);
    }

    @Override
    public User checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String,Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        User user = JSON.parseObject(userJson,User.class);
        return user;
    }

    @Override
    public Result logout() {
//        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public Result regUserAndStu(UserAndStudent userAndStudent) {
        String account = userAndStudent.user.getAccount();
        //判断学号是否存在
        User user1 =this.userService.findUserByAccount(account);
        if (user1 != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        //在判断班级表中的注册人数是不是大于总人数，大于就不让注册       学生注册完后找到班级表
        LambdaQueryWrapper<Class> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Class::getClassName,userAndStudent.student.getClassName());
        Class myClass = classMapper.selectOne(lambdaQueryWrapper);

        BigInteger trueStu = myClass.getTrueStu();
        BigInteger fullStu = myClass.getFullStu();
        BigInteger fillStu = myClass.getFillStu();

        //判断学生注册不能大于总班级人数
        if (trueStu.intValue() > fullStu.intValue()){
            //不可以注册
            return Result.fail(ErrorCode.CLASSFULL_ERROR.getCode(),ErrorCode.CLASSFULL_ERROR.getMsg());
        }else{
            //可以注册
            String password = userAndStudent.user.getPassword();
            userAndStudent.user.setPassword(DigestUtils.md5Hex(password+slat));
            userAndStudent.student.setIsWork(false);
            this.userMapper.insert(userAndStudent.user);
            this.studentMapper.insert(userAndStudent.student);

            //使班级表注册人数加1
            int trueStu1 = trueStu.intValue();
            trueStu1 = trueStu1 + 1;
            System.out.println(trueStu1+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            myClass.setTrueStu(BigInteger.valueOf(trueStu1));

            //总人数减去注册人数就是未注册人数
            int notTrueStu = fullStu.intValue() - trueStu1;
            myClass.setNotTrueStu(BigInteger.valueOf(notTrueStu));

            //已经注册未填写
            int resNotFill = trueStu1 - fillStu.intValue();
            myClass.setResNotFill(BigInteger.valueOf(resNotFill));

            classMapper.updateById(myClass);
        }



        String token = JWTUtils.createToken(userAndStudent.user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(userAndStudent.user),1, TimeUnit.DAYS);
        return Result.success(token);

    }

    @Override
    public User findUserByAccount(String account) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account);
        //确保只能查询一条
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Result regUserAndTea(UserAndTeacher userAndTeacher) {
        String account = userAndTeacher.user.getAccount();
        //判断手机号是否存在
        User user1 =this.userService.findUserByAccount(account);
        if (user1 != null){
            System.out.println("账号已经存在");
            return Result.fail(ErrorCode.PHONE_EXIST.getCode(),ErrorCode.PHONE_EXIST.getMsg());
        }
        String password = userAndTeacher.user.getPassword();
        userAndTeacher.user.setPassword(DigestUtils.md5Hex(password+slat));
        this.userMapper.insert(userAndTeacher.user);
        this.teacherMapper.insert(userAndTeacher.teacher);
        String token = JWTUtils.createToken(userAndTeacher.user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(userAndTeacher.user),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result modifyPassword(ModifyPasswordParam modifyPasswordParam) {

        //根据 用户Id查询数据库老密码查询到数据，若查不到就返回旧密码输入错误联系管理员
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,modifyPasswordParam.getUserId());
        String password = modifyPasswordParam.getOldPassword();
        password = DigestUtils.md5Hex(password+slat);
        queryWrapper.eq(User::getPassword,password);
        //确保只能查询一条
        queryWrapper.last("limit 1");
        User user = userMapper.selectOne(queryWrapper);
        if (user != null){
            //进行改密码,记得密码要加密

            String newPas = modifyPasswordParam.getNewPassword();
            System.out.println(newPas);
            newPas = DigestUtils.md5Hex(newPas+slat);
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",user.getId());
            updateWrapper.set("password",newPas);

            userMapper.update(user,updateWrapper);
            User user1 = userMapper.selectById(user.getId());

            return Result.success(user1);
        }else {
           return Result.fail(ErrorCode.PASSWORD_ERROR.getCode(),ErrorCode.PASSWORD_ERROR.getMsg());
        }


    }

    @Override
    public Result isAccount(String account) {
        User user = userService.findUserByAccount(account);
        if (user != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }else {
            return Result.success();
        }
    }


    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("8888888"+slat));
    }
}

package com.yuanhuafeng.employment.controller;


import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.service.UserService;
import com.yuanhuafeng.employment.vo.params.LoginParam;
import com.yuanhuafeng.employment.vo.UserAndStudent;
import com.yuanhuafeng.employment.vo.UserAndTeacher;
import com.yuanhuafeng.employment.vo.params.ModifyPasswordParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginParam loginParam){
        //登录验证用户 访问用户表
        return userService.login(loginParam);
    }

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return userService.findUserByToken(token);
    }

    @GetMapping("/logout")
    public Result logout(){
        return userService.logout();
    }

    @PostMapping("/regUserAndStu")
    public Result regUserAndStu(@RequestBody UserAndStudent userAndStudent){
        System.out.println(userAndStudent);
        return userService.regUserAndStu(userAndStudent);
    }

    @PostMapping("/regUserAndTea")
    public Result regUserAndTea(@RequestBody UserAndTeacher userAndTeacher){
        return userService.regUserAndTea(userAndTeacher);
    }

    @PostMapping("/modifyPassword")
    public Result modifyPassword(@RequestBody ModifyPasswordParam modifyPasswordParam){
        return userService.modifyPassword(modifyPasswordParam);
    }

    @PostMapping("/isAccount/{account}")
    public Result isAccount(@PathVariable String account){
        return userService.isAccount(account);
    }

}


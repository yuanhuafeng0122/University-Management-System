package com.yuanhuafeng.employment.config;

import com.yuanhuafeng.employment.entity.Teacher;
import com.yuanhuafeng.employment.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private boolean success;
    private int code;
    private String msg;
    private Object data;
    private Object data2;

    public static Result success(){
        return new Result(true,200,"success",null,null);
    }
    public static Result fail(){
        return new Result(false,500,"fail",null,null);
    }

    public static Result success(Object data){
        return new Result(true,200,"success",data,null);
    }

    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null,null);
    }

    public static Result success(Object data,Object data2) {
        return new Result(true,200,"success",data,data2);
    }
}

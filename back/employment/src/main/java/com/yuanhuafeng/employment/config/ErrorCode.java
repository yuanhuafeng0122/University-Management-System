package com.yuanhuafeng.employment.config;

public enum ErrorCode {

    PARAMS_ERROR(10001,"参数有误"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),
    ACCOUNT_ERROR(10005,"账号密码不正确或选择角色错误"),
    TOKEN_ERROR(10003,"token不合法"),
    ACCOUNT_EXIST(10004,"账号已经存在"),
    CLASS_EXIST(10027,"班级已经存在"),
    PHONE_EXIST(10006,"手机号已经存在"),
    PASSWORD_ERROR(10007,"旧密码输入错误，请联系管理员"),
    STUNULL_ERROR(10008,"暂无数据，请通知学生注册"),
    STUNOTFIND_ERROR(10009,"找不到该学生"),
    STUDENTLIST_ERROR(10030,"找不到学生列表"),
    TEANOTFIND_ERROR(10021,"找不到老师"),
    NOTFINDREC_ERROR(100023,"找不到岗位"),
    RECNULL_ERROR(10010,"暂无该岗位信息，请输入其他岗位"),
    EMPNULL_ERROR(10010,"暂无该就业信息"),
    TEANULL_ERROR(10011,"暂无辅导员信息，请通知辅导员注册"),
    DEL_FAIL(10013,"删除失败"),
    NOTFINDMAJOR_ERROR(10024,"找不到专业"),
    NOTFINDNEWS_ERROR(10033,"找不到通知"),
    ADDNEWS_ERROR(10032,"增加新闻失败"),
    EMP_ERROR(10014,"系统错误，就业信息添加失败"),
    MODIFTSTU_ERROR(10015,"更新学生信息失败,请检查是否修正信息"),
    MODIFYNUM_ERROR(10016,"系统错误，更新学生学号失败"),
    MODIFYTEA_ERROR(10017,"系统错误，更新老师信息失败"),
    MODIFYREC_ERROR(10018,"系统错误，更新职位信息失败"),
    TEAINFO_ERROR(10019,"系统错误，请检查是否登录"),
    ERROR(10020,"系统错误"),
    UPDATA_ERROR(10020,"更新失败"),
    ADDCLASS_ERROR(10025,"添加班级失败"),
    FINDCLASSES_ERROR(10025,"找不到班级"),
    FINDCOLLEGE_ERROR(10025,"找不到学院"),
    FINDTEA_ERROR(10026,"找不到班级所对应的老师"),
    CLASSFULL_ERROR(10027,"班级人数已满，请联系老师"),
    DELETECLA_ERROR(10028,"系统错误删除班级失败"),
    ADDREC_ERROR(10031,"添加就业岗位失败"),
    MODIFYCLA_ERROR(10029,"系统错误更新班级信息失败"),
    NOTFINDEUSER(10022,"找不到用户");

    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}



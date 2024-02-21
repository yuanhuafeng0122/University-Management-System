package com.yuanhuafeng.employment.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuanhuafeng.employment.config.ErrorCode;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.*;
import com.yuanhuafeng.employment.entity.Class;
import com.yuanhuafeng.employment.mapper.*;
import com.yuanhuafeng.employment.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuanhuafeng.employment.vo.TeaStuVo;
import com.yuanhuafeng.employment.vo.params.ModifyStuNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 移应19-2-32-袁华峰
 * @since 2021-11-03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired(required = false)
    private StudentMapper studentMapper;

    @Autowired(required = false)
    private TeacherMapper teacherMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private EmployInfoMapper employInfoMapper;
    @Autowired(required = false)
    private ClassMapper classMapper;

    @Override
    public Result insert(Student student) {
        studentMapper.insert(student);
        return Result.success();
    }

    @Override
    public Result findStuByAccount(String userAccount) {
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Student::getNumber,userAccount);
        lambdaQueryWrapper.last("limit 1");
        Student student = studentMapper.selectOne(lambdaQueryWrapper);
        return Result.success(student);
    }

    @Override
    public Result findNameByAccount(String account) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number",account);
        queryWrapper.select("name","major","classes","teacher_name");
        Student student = studentMapper.selectOne(queryWrapper);
        return Result.success(student);
    }

    @Override
    public Result findAllStuByTeaAccount(String account) {

//        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Teacher::getPhone,account);
//        queryWrapper.last("limit 1");
//        Teacher teacher = teacherMapper.selectOne(queryWrapper);
//        String name = teacher.getName();
//
//        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
//        studentQueryWrapper.eq("teacher_name",name);
//        List<Student> list = studentMapper.selectList(studentQueryWrapper);
//        return Result.success(list);
        return Result.fail();

    }

    @Override
    public Result findAllStu() {
        List<Student> list = studentMapper.selectList(null);
        if (list != null){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.STUNULL_ERROR.getCode(), ErrorCode.STUNULL_ERROR.getMsg());
        }
    }

    @Override
    public Result findStuByName(String name,Integer teaId) {


        LambdaQueryWrapper<Student> studentQueryWrapper = new LambdaQueryWrapper<>();
        studentQueryWrapper.like(Student::getName,name).eq(Student::getTeacherId,teaId);
        List<Student> list = studentMapper.selectList(studentQueryWrapper);

        LambdaQueryWrapper<Student> studentQueryWrapper1 = new LambdaQueryWrapper<>();
        studentQueryWrapper1.like(Student::getNumber,name).eq(Student::getTeacherId,teaId);
        List<Student> list1 = studentMapper.selectList(studentQueryWrapper1);

        list.addAll(list1);


        if (list.size() == 0 ){
            return Result.fail(ErrorCode.STUNOTFIND_ERROR.getCode(), ErrorCode.STUNOTFIND_ERROR.getMsg());
        }else {
            return Result.success(list);
        }
    }

    @Override
    public Result deleteStuByNum(String number) {
        //更具学生的学号删除，然后也要删除用户,还要删除对应的就业信息
        Map<String,Object> stuMap = new HashMap<>();
        stuMap.put("number",number);
        int stuRow = studentMapper.deleteByMap(stuMap);


        Map<String,Object> userMap = new HashMap<>();
        userMap.put("account",number);
        int userRow = userMapper.deleteByMap(userMap);

        Map<String,Object> empMap = new HashMap<>();
        empMap.put("number",number);
        int empRow = employInfoMapper.deleteByMap(empMap);


        if (stuRow == 1 && userRow == 1){
            return Result.success();
        }else {
            return Result.fail(ErrorCode.DEL_FAIL.getCode(), ErrorCode.DEL_FAIL.getMsg());
        }

    }

    @Override
    public Result modifyStu(Student student,String oldClassName) {
        int row = studentMapper.updateById(student);
        Student student1 = studentMapper.selectById(student.getId());



        //修改就业信息
        LambdaQueryWrapper<EmployInfo> employInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employInfoLambdaQueryWrapper.eq(EmployInfo::getStudentId,student.getId());
        EmployInfo employInfo = employInfoMapper.selectOne(employInfoLambdaQueryWrapper);
        System.out.println(employInfo+"<<<<<<<");
        if (employInfo != null){
            employInfo.setNumber(student1.getNumber());
            employInfo.setStudentName(student1.getName());
            employInfo.setMajor(student1.getMajor());
            employInfo.setClasses(student1.getClassName());
            Teacher teacher = teacherMapper.selectById(student1.getTeacherId());
            employInfo.setTeacherName(teacher.getName());
            employInfoMapper.updateById(employInfo);
        }
        modifuClass(student ,student.getClassName(),oldClassName);


        if (row != 0){
            return Result.success(student1);
        }else {
            return Result.fail(ErrorCode.MODIFTSTU_ERROR.getCode(), ErrorCode.MODIFTSTU_ERROR.getMsg());
        }

    }

    private void modifuClass(Student student, String newClassName, String oldClassName) {

        //先把这两个班级找到
        LambdaQueryWrapper<Class> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Class::getClassName,oldClassName);
        Class oldClass = classMapper.selectOne(lambdaQueryWrapper);

        LambdaQueryWrapper<Class> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.like(Class::getClassName,newClassName);
        Class newClass = classMapper.selectOne(lambdaQueryWrapper1);

        //得到两个班级的总人数
        BigInteger oldFullStu = oldClass.getFullStu();
        BigInteger newFullStu = newClass.getFullStu();


        if ( !newClassName.equals(oldClassName)){

            //老班级注册人数减一，未注册人数加一，然后再判断是不是填写了
            BigInteger oldTrueStu = oldClass.getTrueStu();
            int oldTrueStu1 = oldTrueStu.intValue() - 1;
            oldClass.setTrueStu(BigInteger.valueOf(oldTrueStu1));
            System.out.println("老班级注册人"+ oldTrueStu1);
            //得到未注册人数
            int oldNotTrueStu = oldFullStu.intValue() - oldTrueStu1;
            System.out.println("老班级未注册人"+oldNotTrueStu);
            oldClass.setNotTrueStu(BigInteger.valueOf(oldNotTrueStu));
            classMapper.updateById(oldClass);

            //新的班级
            BigInteger newTrueStu = newClass.getTrueStu();
            //注册人数加一
            int newTrueStu1 = newTrueStu.intValue() + 1;
            newClass.setTrueStu(BigInteger.valueOf(newTrueStu1));
            System.out.println("新班级注册人"+ newTrueStu1);
            //得到未注册人数
            int newNotTrueStu = newFullStu.intValue() - newTrueStu1;
            newClass.setNotTrueStu(BigInteger.valueOf(newNotTrueStu));
            System.out.println("新班级未注册人"+newNotTrueStu);
            newClass.setNotTrueStu(BigInteger.valueOf(newNotTrueStu));
            classMapper.updateById(newClass);



            //判断是不是填写了
            if (student.getIsWork()){
                //填写了，老板班级填写人数减一，得到未填写人数
                BigInteger oldFillStu = oldClass.getFillStu();
                int oldFillStu1 = oldFillStu.intValue() - 1;
                oldClass.setFillStu(BigInteger.valueOf(oldFillStu1));
                System.out.println("老班级填写人数"+oldFillStu1);
                //得到老班级未填写人数
                int oldNotFill = oldFullStu.intValue() - oldFillStu1;
                oldClass.setNotFillStu(BigInteger.valueOf(oldNotFill));
                //得到已注册未填写
                int notFill =  oldTrueStu1 - oldFillStu1;
                oldClass.setResNotFill(BigInteger.valueOf(notFill));
                classMapper.updateById(oldClass);



                //填写人数加一
                BigInteger newFillStu = newClass.getFillStu();
                int newFillStu1 = newFillStu.intValue() + 1;
                System.out.println(newFillStu1+"新班级的填写人数");
                newClass.setFillStu(BigInteger.valueOf(newFillStu1));

                //已注册未填写
                BigInteger newResStu = newClass.getTrueStu();
                int trueNotFill = newResStu.intValue() - newFillStu1;
                newClass.setResNotFill(BigInteger.valueOf(trueNotFill));

                int notFillStu = newFullStu.intValue() - newFillStu1;
                newClass.setNotFillStu(BigInteger.valueOf(notFillStu));
                classMapper.updateById(newClass);


            }else {
                //老班级没有填写,填写人数不动，已注册未填写等于注册人数-填写人数
                BigInteger oldFillStu = oldClass.getFillStu();
                int oldResNotFillStu = oldTrueStu1 - oldFillStu.intValue();
                oldClass.setResNotFill(BigInteger.valueOf(oldResNotFillStu));
                //未填写人数 == 总人数减去填写
                int oldNotFillStu =  oldFullStu.intValue() - oldFillStu.intValue();
                oldClass.setNotFillStu(BigInteger.valueOf(oldNotFillStu));
                classMapper.updateById(oldClass);


                //新班级没有填写,填写人数不动，已注册未填写等于注册人数-填写人数
                BigInteger tureNotFillStu = newClass.getResNotFill();
                int tureNotFillStu1 = tureNotFillStu.intValue() + 1;
                newClass.setResNotFill(BigInteger.valueOf(tureNotFillStu1));
                classMapper.updateById(newClass);
            }

        }

    }

    @Override
    public Result modifyStuNum(ModifyStuNum modifyStuNum) {

        //更具id查询到学生，在把老密码改成传来的新密码
        Student student = studentMapper.selectById(modifyStuNum.getStuId());

        if (student != null){
            student.setNumber(modifyStuNum.getNewNumber());
            studentMapper.updateById(student);
            return Result.success();
        }else {
            return Result.fail(ErrorCode.MODIFYNUM_ERROR.getCode(), ErrorCode.MODIFYNUM_ERROR.getMsg());
        }

    }

    @Override
    public Result findStuByUserAccount(String account) {
        //通过user的账号找到老师的Id在通过老师Id找到老师所对应的学生
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getPhone,account);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);

        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getTeacherId,teacher.getId());
        List<Student> list = studentMapper.selectList(studentLambdaQueryWrapper);
        if (list.size() == 0 ){
            return Result.fail(ErrorCode.STUDENTLIST_ERROR.getCode(), ErrorCode.STUDENTLIST_ERROR.getMsg());
        }else {
            return Result.success(list);
        }
    }

    @Override
    public Result findOneStuByNumber(String account) {
        //通过用户的账号找出唯一的一个学生
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getNumber,account).last("limit 1");
        Student student = studentMapper.selectOne(queryWrapper);
        if (student != null){
            return Result.success(student);
        }else {
            return Result.fail(ErrorCode.STUNOTFIND_ERROR.getCode(),ErrorCode.STUNOTFIND_ERROR.getMsg());
        }

    }

    @Override
    public Result modiFyStu(Student student,String account,String oldClassName) {
        //更新user表的账号

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccount,account).last("limit 1");
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        user.setAccount(student.getNumber());

        //更新自己的信息
        int row = studentMapper.updateById(student);
        Student student1 = studentMapper.selectById(student.getId());

        //修改就业信息
        LambdaQueryWrapper<EmployInfo> employInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employInfoLambdaQueryWrapper.eq(EmployInfo::getStudentId,student.getId());
        EmployInfo employInfo = employInfoMapper.selectOne(employInfoLambdaQueryWrapper);
        System.out.println(employInfo+"<<<<<<<");
        if (employInfo != null){
            employInfo.setNumber(student1.getNumber());
            employInfo.setStudentName(student1.getName());
            employInfo.setMajor(student1.getMajor());
            employInfo.setClasses(student1.getClassName());
            Teacher teacher = teacherMapper.selectById(student1.getTeacherId());
            employInfo.setTeacherName(teacher.getName());
            employInfoMapper.updateById(employInfo);
        }


        modifuClass(student ,student.getClassName(),oldClassName);


        if (row != 0){
            userMapper.updateById(user);
            return Result.success(student1);
        }else {
            return Result.fail(ErrorCode.MODIFTSTU_ERROR.getCode(), ErrorCode.MODIFTSTU_ERROR.getMsg());
        }


    }

    @Override
    public Result getAllTeaStu() {
        List<TeaStuVo> teaStuVoList =  studentMapper.getAllTeaStu();
        if (teaStuVoList.size()!=0){
            return Result.success(teaStuVoList);
        }else {
            return Result.fail(ErrorCode.STUNOTFIND_ERROR.getCode(),ErrorCode.STUNOTFIND_ERROR.getMsg());
        }

    }

    @Override
    public Result findStuById(Integer id) {
        TeaStuVo teaStuVo = studentMapper.findStuById(id);
        if (teaStuVo!=null){
            return Result.success(teaStuVo);
        }else {
            return Result.fail(ErrorCode.STUNOTFIND_ERROR.getCode(),ErrorCode.STUNOTFIND_ERROR.getMsg());
        }
    }

    @Override
    public Result findStuByTeaId(Integer teaId) {

        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getTeacherId, teaId);
        List<Student> list = studentMapper.selectList(studentLambdaQueryWrapper);
        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.STUNOTFIND_ERROR.getCode(),ErrorCode.STUNOTFIND_ERROR.getMsg());
        }
    }

    @Override
    public Result deleteStuById(Integer stuId) {
        int row  = studentMapper.deleteById(stuId);
        if (row != 0){
            return Result.success();
        }else {
            return Result.fail(00,"删除失败");
        }


    }

    @Override
    public Result findStuByClassNameAndTeaId(String className,Integer teaId) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.like(Student::getClassName, className)
                .and(studentLambdaQueryWrapper1 -> studentLambdaQueryWrapper1.eq(Student::getTeacherId,teaId));;
        List<Student> list = studentMapper.selectList(studentLambdaQueryWrapper);
        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.STUNOTFIND_ERROR.getCode(),ErrorCode.STUNOTFIND_ERROR.getMsg());
        }
    }

    @Override
    public Result findStuByMajorAndTeaId(String major,Integer teaId) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.like(Student::getMajor, major)
                .and(studentLambdaQueryWrapper1 -> studentLambdaQueryWrapper1.eq(Student::getTeacherId,teaId));
        List<Student> list = studentMapper.selectList(studentLambdaQueryWrapper);
        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.STUNOTFIND_ERROR.getCode(),ErrorCode.STUNOTFIND_ERROR.getMsg());
        }
    }

    @Override
    public Result findStuByClassName(String className) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.like(Student::getClassName, className);
        List<Student> list = studentMapper.selectList(studentLambdaQueryWrapper);
        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.STUNOTFIND_ERROR.getCode(),ErrorCode.STUNOTFIND_ERROR.getMsg());
        }
    }

    @Override
    public Result findStuByMajor(String major) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.like(Student::getMajor, major);
        List<Student> list = studentMapper.selectList(studentLambdaQueryWrapper);
        if (list.size()!=0){
            return Result.success(list);
        }else {
            return Result.fail(ErrorCode.STUNOTFIND_ERROR.getCode(),ErrorCode.STUNOTFIND_ERROR.getMsg());
        }
    }


}

package com.yuanhuafeng.employment.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuanhuafeng.employment.config.Result;
import com.yuanhuafeng.employment.entity.EmployInfo;
import com.yuanhuafeng.employment.mapper.EmployInfoMapper;
import cn.hutool.core.io.IoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.rmi.CORBA.Util;
import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/export")
public class ExportExcelController {

    @Autowired(required = false)
    private EmployInfoMapper employInfoMapper;

    @GetMapping("/exportEmp")
    public Result export(HttpServletResponse response) throws IOException{
        List<Map<String,Object>> list = CollUtil.newArrayList();
        List<EmployInfo> all = employInfoMapper.selectList(null);

        for (EmployInfo employInfo : all){
            Map<String,Object> row = new LinkedHashMap<>();
            row.put("学号",employInfo.getNumber());
            row.put("姓名",employInfo.getStudentName());
            row.put("专业",employInfo.getMajor());
            row.put("去向",employInfo.getGraduationGoto());
            row.put("岗位",employInfo.getPost());
            row.put("地区",employInfo.getDtaAddress());
            list.add(row);
        }

        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("就业信息表","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();

        writer.flush(outputStream,true);
        writer.close();
        IoUtil.close(System.out);
        return Result.success();
    }

    @GetMapping("/exportEmp1/{className}")
    public Result export1(HttpServletResponse response, @PathVariable String className) throws IOException{
        List<Map<String,Object>> list = CollUtil.newArrayList();
        LambdaQueryWrapper<EmployInfo> employInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employInfoLambdaQueryWrapper.like(EmployInfo::getClasses,className);
        List<EmployInfo> all = employInfoMapper.selectList(employInfoLambdaQueryWrapper);

        for (EmployInfo employInfo : all){
            Map<String,Object> row = new LinkedHashMap<>();
            row.put("专业",employInfo.getMajor());
            row.put("班级",employInfo.getClasses());
            row.put("学号",employInfo.getNumber());
            row.put("姓名",employInfo.getStudentName());
            row.put("去向",employInfo.getGraduationGoto());
            row.put("岗位",employInfo.getPost());
            row.put("地区",employInfo.getDtaAddress());
            list.add(row);
        }

        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list,true);
        // 下载文件的默认名称
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode(className+"就业信息表","UTF-8");
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();

        writer.flush(outputStream,true);
        writer.close();
        IoUtil.close(System.out);
        return Result.success();

    }

}

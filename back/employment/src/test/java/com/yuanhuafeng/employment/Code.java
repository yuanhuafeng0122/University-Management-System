package com.yuanhuafeng.employment;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.val;

public class Code {
    public static void main(String[] args) {
        //需要构建一个 代码生成器 对象
        AutoGenerator autoGenerator = new AutoGenerator();

        //配置策略

        //1、全局配置
        GlobalConfig globalConfig =new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath+"/src/main/java");
        globalConfig.setAuthor("移应19-2-32-袁华峰");
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(false);//是否覆盖
        globalConfig.setServiceName("%sService");
        globalConfig.setIdType(IdType.AUTO);
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setSwagger2(false);
        autoGenerator.setGlobalConfig(globalConfig);

        //配置DataSourceConfig数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/bysz?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        dataSourceConfig.setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dataSourceConfig);

        //配置包
        PackageConfig packageConfig = new PackageConfig();
//        packageConfig.setModuleName("");
        packageConfig.setParent("com.yuanhuafeng.employment");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setController("controller");
        autoGenerator.setPackageInfo(packageConfig);

        //配置策略
        StrategyConfig strategyConfig =new StrategyConfig();
        strategyConfig.setInclude("");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategyConfig.setSuperEntityClass("你自己的父类实体没有就不要设置");
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setEntityLombokModel(true);
//        strategyConfig.setRestControllerStyle(true);


        autoGenerator.setStrategy(strategyConfig);

        //执行代码构造器
        autoGenerator.execute();
    }
}

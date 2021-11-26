package com.lecture.infr.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;


/**
 * @className: MyBatisConfig
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/26 11:00
 */
@Configuration
@MapperScan("com.lecture.infr.dao")
public class MyBatisConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean () throws Exception{
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/**/*.xml"));
        factoryBean.setTypeHandlersPackage("com.lecture.infr.config");
        // 分页插件暂时不定
        // 实体类别名 别名冲突暂时不使用别名 WarehouseLocationDO
//        factoryBean.setTypeAliasesPackage("com.shopcider.plutus.infr.*.entity");
        MybatisConfiguration configuration = new MybatisConfiguration();
        //开启下划线转驼峰
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        factoryBean.setGlobalConfig(globalConfig);
        factoryBean.setConfiguration(configuration);
        return factoryBean;
    }
}

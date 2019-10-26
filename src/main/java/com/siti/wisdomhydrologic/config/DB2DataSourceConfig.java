package com.siti.wisdomhydrologic.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.siti.wisdomhydrologic.nid", sqlSessionFactoryRef = "DB2Factory")
public class DB2DataSourceConfig {

    @Bean(name = "DB2dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource DB1dataSource(){
        return new DruidDataSource();
    }

    @Bean(name = "DB2Factory")
    public SqlSessionFactory DB1Factory(@Qualifier("DB2dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        //这里注意, 设置该参数时打成jar启动会找不到该包下的类,目前未找到解决方案
        bean.setTypeAliasesPackage("com.siti.wisdomhydrologic.nid");
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:DB2Mapper/*.xml"));
        return bean.getObject();
    }

}
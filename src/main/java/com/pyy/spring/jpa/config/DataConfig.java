package com.pyy.spring.jpa.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;
//        持久化配置
//        启动spring事务处理理
//        指定repository位置
//        配置数据库连接属性文件（会放入环境变量）
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.pyy.spring.jpa.repository")
@PropertySource("classpath:database.properties")
public class DataConfig {

    private final String PROPERTY_DRIVER = "driver";
    private final String PROPERTY_URL = "url";
    private final String PROPERTY_USERNAME = "user";
    private final String PROPERTY_PASSWORD = "password";
    private final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
    private final String PROPERTY_DIALECT = "hibernate.dialect";

    @Autowired
    Environment environment;

    //实体类bean工厂
    // 配置数据源，持久化提供商（hibernate），实体类（模型类）位置
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(){

        LocalContainerEntityManagerFactoryBean lfb  = new LocalContainerEntityManagerFactoryBean();
        lfb.setDataSource(dataSource());
        lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        lfb.setPackagesToScan("com.pyy.spring.jpa.model");
        lfb.setJpaProperties(hibernateProps());
        return lfb;
    }

    //根据数据源驱动，URI，⽤用户名和密码配置数据源
    @Bean
    DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(environment.getProperty(PROPERTY_URL));
        ds.setDriverClassName(environment.getProperty(PROPERTY_DRIVER));
        ds.setUsername(environment.getProperty(PROPERTY_USERNAME));
        ds.setPassword(environment.getProperty(PROPERTY_PASSWORD));
        return ds;

    }

    //配置hibernate属性
    Properties hibernateProps(){

        Properties properties  = new Properties();
        properties.setProperty(PROPERTY_DIALECT,environment.getProperty(PROPERTY_DIALECT));
        properties.setProperty(PROPERTY_SHOW_SQL,environment.getProperty(PROPERTY_SHOW_SQL));
        return properties;
    }

    //事务
    @Bean
    JpaTransactionManager transactionManager(){

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }


}

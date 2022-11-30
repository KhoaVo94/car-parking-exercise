package com.exercise.carparking;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.exercise.carparking.mapper")
public class MyBatisConfig {
    @Primary
    @Bean(name="registerDataSource")
    @ConfigurationProperties(prefix="spring.datasource.register")
    DataSource registerDataSource()
    {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="queryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.query")
    DataSource searchDataSource()
    {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name="registerSqlSessionFactory")
    SqlSessionFactory sqlRegisterSessionFactory(@Qualifier("registerDataSource") DataSource dataSource) throws Exception {
        return create(dataSource);
    }

    @Bean(name="querySqlSessionFactory")
    SqlSessionFactory sqlSearchSessionFactory(@Qualifier("queryDataSource") DataSource dataSource) throws Exception {
        return create(dataSource);
    }

    @Primary
    @Bean(name="registerSessionTemplate")
    SqlSessionTemplate sqlRegisterSessionTemplate(@Qualifier("registerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name="querySessionTemplate")
    SqlSessionTemplate sqlSearchSessionTemplate(@Qualifier("querySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Value("${mybatis.mapper-locations}")
    String resource;

    private SqlSessionFactory create(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());

        sessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis.xml"));

        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        sessionFactoryBean.setMapperLocations(resolver.getResources(resource));

        return sessionFactoryBean.getObject();
    }
}

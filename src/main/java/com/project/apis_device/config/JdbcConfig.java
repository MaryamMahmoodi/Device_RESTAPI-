package com.project.apis_device.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.project")
@PropertySource("classpath:config/jdbc/db.properties")
public class JdbcConfig
{

    @Value("${spring.datasource.driver}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String pass;

    @Value("${spring.datasource.url}")
    private String url;


    @Bean
    @Primary
    public DataSource jdbcMysqlDataSource()
    {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(pass)
                .driverClassName(driver)
                .build();
    }

    @Bean
    public JdbcTemplate mysqlJdbcTemplate(DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource)
    {
        return new NamedParameterJdbcTemplate(dataSource);
    }


    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource)
    {
        return new JdbcTransactionManager(dataSource);
    }

}

package com.rpatino12.epam.gym.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.rpatino12.epam.gym")
@PropertySource("classpath:application.properties")
public class AppConfig {
    // This is just for the example, it's not a good practice to do this
    // The best way is using environmental variables to set the database connection
    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${driver}")
    private String driver;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    // We can create a bean to set the database connection and inject the dependency
    // Note: If you want to see the database console using h2-console (go to http://localhost:8080/h2-console/)
    @Bean
    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        return dataSourceBuilder.driverClassName(driver)
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }
}

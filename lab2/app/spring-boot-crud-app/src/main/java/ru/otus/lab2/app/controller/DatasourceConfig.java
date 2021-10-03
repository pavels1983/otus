package ru.otus.lab2.app.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DatasourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String pgHost = System.getProperty("POSTGRES_HOST");
        pgHost = (pgHost == null || pgHost.isEmpty()) ? "postgres" : pgHost;
        String pgPort = System.getProperty("POSTGRES_PORT");
        pgPort = (pgPort == null || pgPort.isEmpty()) ? "5432" : pgPort;

        dataSource.setUrl("jdbc:postgresql://" + pgHost + ":" + pgPort + "/" + System.getProperty("POSTGRES_DB"));
        dataSource.setUsername(System.getProperty("POSTGRES_USER"));
        dataSource.setPassword(System.getProperty("POSTGRES_PASSWORD"));
        System.out.println("POSTGRES_DB=" + System.getProperty("POSTGRES_DB"));
        System.out.println("POSTGRES_USER=" + System.getProperty("POSTGRES_USER"));
        System.out.println("POSTGRES_PASSWORD=" + System.getProperty("POSTGRES_PASSWORD"));
        return dataSource;
    }

}
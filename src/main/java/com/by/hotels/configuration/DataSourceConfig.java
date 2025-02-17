package com.by.hotels.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    @Bean
    @Profile("postgresql")
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/hotels_db")
                .username("postgres")
                .password("q1235322")
                .build();
    }

    @Bean
    @Profile("mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/hotels_db")
                .username("root")
                .password("q1235322")
                .build();
    }

    @Bean
    @Profile("h2")
    public DataSource h2DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:file:./data/hotels_db")
                .username("sa")
                .password("null")
                .build();
    }

}
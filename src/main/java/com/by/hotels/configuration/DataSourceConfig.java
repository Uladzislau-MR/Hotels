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
    @Profile("default")

    public DataSource h2DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:file:./data/hotels_db")
                .username("sa")
                .password("")
                .build();
    }


//    @Bean
//    @Profile("default")
//    public SpringLiquibase h2Liquibase(
//            @Qualifier("h2DataSource") DataSource dataSource) {
//        return configureLiquibase(dataSource);
//    }
//
//
//    private SpringLiquibase configureLiquibase(DataSource dataSource) {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setChangeLog("classpath:/db/changelog/db.changelog-master.xml");
//        liquibase.setDataSource(dataSource);
//        liquibase.setShouldRun(true);
//        return liquibase;
//    }
}

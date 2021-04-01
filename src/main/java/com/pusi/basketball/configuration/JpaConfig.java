package com.pusi.basketball.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    @Bean
    public DataSource mySQLDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:%s", System.getenv("CLEARDB_DATABASE_URL")));

        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(2);
        config.setIdleTimeout(600000);
        config.setConnectionTimeout(30000);
        config.setMaxLifetime(1800000);
        return new HikariDataSource(config);
    }
}

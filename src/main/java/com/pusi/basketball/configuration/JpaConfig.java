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
        config.setJdbcUrl(String.format("jdbc:%s", System.getenv("POSTGRES_DATABASE_URL")));
        config.setUsername(System.getenv("POSTGRES_DATABASE_USERNAME"));
        config.setPassword(System.getenv("POSTGRES_DATABASE_PASSWORD"));

        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(2);
        config.setIdleTimeout(600000);
        config.setConnectionTimeout(30000);
        config.setMaxLifetime(1800000);
        return new HikariDataSource(config);
    }
}

package com.pusi.basketball.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Value("#{'${spring.security.cors.allowed-origins:*}'.split(',')}")
    private List<String> allowedOrigins;

    private final HeaderAuthenticationFilter headerAuthenticationFilter;

    public AuthConfig(final HeaderAuthenticationFilter headerAuthenticationFilter) {
        this.headerAuthenticationFilter = headerAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .and()
                .addFilterAfter(headerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);

        return urlBasedCorsConfigurationSource;
    }
}

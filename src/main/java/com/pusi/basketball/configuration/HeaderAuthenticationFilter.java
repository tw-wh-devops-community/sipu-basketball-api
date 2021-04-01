package com.pusi.basketball.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class HeaderAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(HeaderAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String xApiKey = request.getHeader("x-api-key");

        if ("/health-check".equals(request.getServletPath())
                || (xApiKey != null && xApiKey.equals(System.getenv("X_API_KEY")))) {
            filterChain.doFilter(request, response);
        } else {
            LOG.warn("Unauthorized http request with x-api-key: {}", xApiKey);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}

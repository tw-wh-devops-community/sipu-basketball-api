package com.pusi.basketball.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthCheckControllerTest {

    @Test
    public void should_response_health_status() {
        HealthCheckController controller = new HealthCheckController();
        assertEquals(HttpStatus.OK, controller.healthCheck().getStatusCode());
        assertEquals("Hi, I am healthy!", controller.healthCheck().getBody());
    }

}

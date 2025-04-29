package com.alejandro.controlgastos;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.alejandro.controlgastos.services.ExpenseService;

// We use this class to create components in the test context
@TestConfiguration
public class TestConfig {
    
    // Create the component that represents the service to mock
    @Bean
    public ExpenseService nombreEntidadXService() {
        return Mockito.mock(ExpenseService.class);
    }

}

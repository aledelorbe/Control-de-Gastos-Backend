package com.alejandro.controlgastos.integrations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alejandro.controlgastos.entities.Budget;
import com.alejandro.controlgastos.repositories.BudgetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BudgetIntegrationTest {

    // To inject the component of testRestTemplate
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private BudgetRepository repository;

    @Autowired
    private ObjectMapper objectMapper;
    
    // To load the data
    @BeforeEach
    void setUp() throws IOException {

        InputStream inputStream = getClass().getResourceAsStream("/budgetsData.json");
        List<Budget> budgets = Arrays.asList(objectMapper.readValue(inputStream, Budget[].class));

        repository.saveAll(budgets);
    } 

    // To reset the data at the end of each test method 
    @AfterEach
    void clean() {
        repository.deleteAll();
    }

    // To test the endpoint getBudgets
    @Test
    void getBudgetsIntegrationTest() {

        // When
        ResponseEntity<Budget[]> response  = client.getForEntity("/api/budgets", Budget[].class);
        List<Budget> budgets = Arrays.asList(response.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(budgets.isEmpty());
        assertEquals(1, budgets.size());
        assertEquals("1000000", budgets.get(0).getId());
        assertEquals(23000, budgets.get(0).getAmount());

    }

    // To test the endpoint save
    @Test
    void postBudgetIntegrationTest() {

        // Given
        Budget budgetInsert = new Budget(null, 50000);

        // When
        ResponseEntity<Budget> response = client.postForEntity("/api/budgets", budgetInsert, Budget.class);
        Budget newBudget = response.getBody();

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(50000, newBudget.getAmount());

    }

    // To test the endpoint deleteAll
    @Test
    void deleteAllIntegrationTest() {

        // When
        ResponseEntity<?> response = client.exchange("/api/budgets", HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        // When
        ResponseEntity<Budget[]> response2  = client.getForEntity("/api/budgets", Budget[].class);
        List<Budget> budgets = Arrays.asList(response2.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertTrue(budgets.isEmpty());
        assertEquals(0, budgets.size());

    }

}

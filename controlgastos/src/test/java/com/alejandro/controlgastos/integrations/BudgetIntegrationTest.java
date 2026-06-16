package com.alejandro.controlgastos.integrations;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;

import com.alejandro.controlgastos.entities.Budget;
import com.alejandro.controlgastos.repositories.BudgetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


class BudgetIntegrationTest extends AbstractMongoDbIntegrationTest {

    // To inject the component of testRestTemplate
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/budgets";

    private static final String PUT_DELETE_URL = BASE_URL + "/";


    // To load the data
    @BeforeEach
    void setUp() throws Exception {

        budgetRepository.deleteAll();

        File file =
            ResourceUtils.getFile(
                "classpath:budgetsData.json"
            );

        List<Budget> lista =
            objectMapper.readValue(
                file,
                new TypeReference<List<Budget>>() {}
            );

        budgetRepository.saveAll(lista);
    } 

    // To test the getBudgets endpoint  
    @Test
    void getBudgetsIntegrationTest() {

        // When
        ResponseEntity<Budget[]> response  = testRestTemplate.getForEntity(BASE_URL, Budget[].class);
        List<Budget> budgets = Arrays.asList(response.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(budgets.isEmpty());
        assertEquals(1, budgets.size());
        assertEquals("1000000", budgets.get(0).getId());
        assertEquals(23000, budgets.get(0).getAmount());

    }

    // To test the save endpoint 
    @Test
    void postBudgetIntegrationTest() {

        // Given
        Budget budgetInsert = new Budget(null, 50000);

        // When
        ResponseEntity<Budget> response = testRestTemplate.postForEntity(BASE_URL, budgetInsert, Budget.class);
        Budget newBudget = response.getBody();

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(50000, newBudget.getAmount());

    }

    // To test the update endpoint when we use an existing id 
    @Test
    void putUpdateExistingIdIntegrationTest()  {

        // Given
        String idToUpdate = "1000000";
        Budget budgetToUpdate = new Budget(null, 12000);
        
        // When
        HttpEntity<Budget> request = new HttpEntity<>(budgetToUpdate);
        ResponseEntity<Budget> response = testRestTemplate.exchange(PUT_DELETE_URL + idToUpdate, HttpMethod.PUT, request, Budget.class);

        // Then
        Budget budgetUpdate = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("1000000", budgetUpdate.getId());
        assertEquals(12000, budgetUpdate.getAmount());

    }

    // To test the update endpoint when we use an unexisting id
    @Test
    void putUpdateUnexistingIdIntegrationTest()  {

        // Given
        String idToUpdate = "999999";
        Budget budgetToUpdate = new Budget(null, 12000);
        HttpEntity<Budget> request = new HttpEntity<>(budgetToUpdate);
        
        // When
        ResponseEntity<?> response = testRestTemplate.exchange(PUT_DELETE_URL + idToUpdate, HttpMethod.PUT, request, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the deleteAllendpoint
    @Test
    void deleteAllIntegrationTest() {

        // When
        ResponseEntity<?> response = testRestTemplate.exchange(BASE_URL, HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        // When
        ResponseEntity<Budget[]> response2  = testRestTemplate.getForEntity(BASE_URL, Budget[].class);
        List<Budget> budgets = Arrays.asList(response2.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertTrue(budgets.isEmpty());
        assertEquals(0, budgets.size());

    }

}

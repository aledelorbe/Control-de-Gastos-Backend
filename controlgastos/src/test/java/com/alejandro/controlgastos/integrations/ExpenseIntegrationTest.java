package com.alejandro.controlgastos.integrations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alejandro.controlgastos.entities.Expense;
import com.alejandro.controlgastos.repositories.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

// To start the test context with a random port
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ExpenseIntegrationTest {
    
    // To inject the component of testRestTemplate
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    // To load the data
    @BeforeEach
    void setUp() throws IOException {

        InputStream inputStream = getClass().getResourceAsStream("/expensesData.json");
        List<Expense> expenses = Arrays.asList(objectMapper.readValue(inputStream, Expense[].class));

        repository.saveAll(expenses);
    }

    // To reset the data at the end of each test method 
    @AfterEach
    void clean() {
        repository.deleteAll();
    }

    // To test the endpoint getExpenses
    @Test
    void getExpensesIntegrationTest() {

        // When
        ResponseEntity<Expense[]> response  = client.getForEntity("/api/expenses", Expense[].class);
        List<Expense> expenses = Arrays.asList(response.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(expenses.isEmpty());
        assertEquals(4, expenses.size());
        assertEquals("1000000", expenses.get(0).getId());
        assertEquals("Netflix", expenses.get(0).getName());
        assertEquals(400, expenses.get(0).getAmount());
        assertEquals("Suscripciones", expenses.get(0).getCategory());
        assertEquals(LocalDateTime.of(2025, 4, 25, 14, 30), expenses.get(0).getCreatedAt());

    }

    // To test the endpoint save
    @Test
    void postExpenseIntegrationTest() {

        // Given
        Expense expenseInsert = new Expense(null, "Frappe", 50, "Diversión", LocalDateTime.of(2025, 4, 28, 18, 15));

        // When
        ResponseEntity<Expense> response = client.postForEntity("/api/expenses", expenseInsert, Expense.class);
        Expense newExpense = response.getBody();

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Frappe", newExpense.getName());
        assertEquals(50, newExpense.getAmount());
        assertEquals("Diversión", newExpense.getCategory());
        assertEquals(LocalDateTime.of(2025, 4, 28, 18, 15), newExpense.getCreatedAt());

    }

    // To test the endpoint update when we use an existing id 
    @Test
    void putUpdateExistingIdIntegrationTest()  {

        // Given
        String idToUpdate = "2000000";
        Expense expenseToUpdate = new Expense(null, "veterinario", 250, "Salud", LocalDateTime.of(2025, 4, 28, 18, 15));
        
        // When
        HttpEntity<Expense> request = new HttpEntity<>(expenseToUpdate);
        ResponseEntity<Expense> response = client.exchange("/api/expenses/" + idToUpdate, HttpMethod.PUT, request, Expense.class);

        // Then
        Expense expenseUpdate = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("2000000", expenseUpdate.getId());
        assertEquals("veterinario", expenseUpdate.getName());
        assertEquals(250, expenseUpdate.getAmount());
        assertEquals("Salud", expenseUpdate.getCategory());
        assertEquals(LocalDateTime.of(2025, 3, 15, 12, 20), expenseUpdate.getCreatedAt());
        assertTrue(Duration.between(expenseUpdate.getUpdateAt(), LocalDateTime.now()).toMinutes() < 2);

    }

    // To test the endpoint update when we use an inexisting id 
    @Test
    void putUpdateInexistingIdIntegrationTest()  {

        // Given
        String idToUpdate = "999999";
        Expense expenseToUpdate = new Expense(null, "veterinario", 250, "Salud", LocalDateTime.of(2025, 4, 28, 18, 15));
        HttpEntity<Expense> request = new HttpEntity<>(expenseToUpdate);
        
        // When
        ResponseEntity<?> response = client.exchange("/api/expenses/" + idToUpdate, HttpMethod.PUT, request, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint delete when we use an existing id 
    @Test
    void deleteExistingIdIntegrationTest() {

        // Given
        String idToDelete = "3000000";
        
        // When
        ResponseEntity<Expense> response = client.exchange("/api/expenses/" + idToDelete, HttpMethod.DELETE, null, Expense.class);

        // Then
        Expense expenseDelete = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("3000000", expenseDelete.getId());
        assertEquals("Doctor", expenseDelete.getName());
        assertEquals(110, expenseDelete.getAmount());
        assertEquals("Salud", expenseDelete.getCategory());
        assertEquals(LocalDateTime.of(2022, 2, 10, 8, 10), expenseDelete.getCreatedAt());

        // When
        ResponseEntity<Expense[]> response2  = client.getForEntity("/api/expenses", Expense[].class);
        List<Expense> expenses = Arrays.asList(response2.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertFalse(expenses.isEmpty());
        assertEquals(3, expenses.size());
        
    }

    // To test the endpoint delete when we use an inexisting id 
    @Test
    void deleteInexistingIdIntegrationTest() {
    
        // Given
        String idToDelete = "999999";
        
        // When
        ResponseEntity<?> response = client.exchange("/api/expenses/" + idToDelete, HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint deleteAll
    @Test
    void deleteAllIntegrationTest() {

        // When
        ResponseEntity<?> response = client.exchange("/api/expenses", HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        // When
        ResponseEntity<Expense[]> response2  = client.getForEntity("/api/expenses", Expense[].class);
        List<Expense> expenses = Arrays.asList(response2.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertTrue(expenses.isEmpty());
        assertEquals(0, expenses.size());

    }

}

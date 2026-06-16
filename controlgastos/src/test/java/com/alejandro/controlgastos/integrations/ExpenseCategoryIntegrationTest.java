package com.alejandro.controlgastos.integrations;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;

import com.alejandro.controlgastos.entities.ExpenseCategory;
import com.alejandro.controlgastos.repositories.ExpenseCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


class ExpenseCategoryIntegrationTest extends AbstractMongoDbIntegrationTest {

    // To inject the component of testRestTemplate
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/expense-categories";

    @BeforeEach
    void setUp() throws Exception {

        expenseCategoryRepository.deleteAll();

        File file =
            ResourceUtils.getFile(
                "classpath:expenseCategories.json"
            );

        List<ExpenseCategory> categories =
            objectMapper.readValue(
                file,
                new TypeReference<List<ExpenseCategory>>() {}
            );

        expenseCategoryRepository.saveAll(categories);
    }

    // To test the expenseCategories endpoint
    @Test
    void expenseCategoriesIntegrationTest() {

        // When
        ResponseEntity<ExpenseCategory[]> response  = testRestTemplate.getForEntity(BASE_URL, ExpenseCategory[].class);
        List<ExpenseCategory> ExpenseCategories = Arrays.asList(response.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(ExpenseCategories.isEmpty());
        assertEquals(2, ExpenseCategories.size());
        assertEquals("0000001", ExpenseCategories.get(0).getId());
        assertEquals("home", ExpenseCategories.get(0).getCategoryName());

    }
    
}
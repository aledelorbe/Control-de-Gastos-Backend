package com.alejandro.controlgastos.controllers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.alejandro.controlgastos.data.ExpenseCategoryData;
import com.alejandro.controlgastos.entities.ExpenseCategory;
import com.alejandro.controlgastos.services.cache.ExpenseCategoryCacheService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(ExpenseCategoryController.class)
class ExpenseCategoryControllerTest {

    // To inject the dependency that allows for mocking HTTP requests
    @Autowired
    private MockMvc mockMvc; 

    // To inject the dependency that represents the service to mock
    // @Autowired
    @MockitoBean
    private ExpenseCategoryCacheService expenseCategoryCacheService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/expense-categories";


    // To test the getExpenseCategories endpoint
    @Test
    void getExpenseCategoriesTest () throws Exception {

        // Given
        when(expenseCategoryCacheService.getExpenseCategories()).thenReturn(ExpenseCategoryData.createExpenseCategories001());

        // When
        MvcResult result = mockMvc.perform(get(BASE_URL))

        // Then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(2)))

            .andExpect(jsonPath("$[0].categoryName").value("home"))
            .andExpect(jsonPath("$[1].categoryName").value("healthy"))
            .andReturn()
        ;

        // Convert the response to a list of objects
        String jsonString = result.getResponse().getContentAsString();
        List<ExpenseCategory> expenseCategories = Arrays.asList(objectMapper.readValue(jsonString, ExpenseCategory[].class));

        assertFalse(expenseCategories.isEmpty());
        assertEquals(2, expenseCategories.size());

        assertEquals("home", expenseCategories.get(0).getCategoryName());
        assertEquals("healthy", expenseCategories.get(1).getCategoryName());

        verify(expenseCategoryCacheService).getExpenseCategories();
    } 

}
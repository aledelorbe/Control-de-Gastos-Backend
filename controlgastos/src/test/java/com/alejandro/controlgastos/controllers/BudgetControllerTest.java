package com.alejandro.controlgastos.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.alejandro.controlgastos.TestConfig;
import com.alejandro.controlgastos.data.BudgetData;
import com.alejandro.controlgastos.entities.Budget;
import com.alejandro.controlgastos.services.BudgetService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BudgetController.class)
@Import(TestConfig.class) 
class BudgetControllerTest {

    // To inject the dependency that allows for mocking HTTP requests
    @Autowired
    private MockMvc mockMvc;

    // To inject the dependency that represents the service to mock
    @Autowired
    private BudgetService service; 

    @Autowired
    private ObjectMapper objectMapper;

    // To test the endpoint getBudgets
    @Test
    void getBudgetsTest () throws Exception {

        // Given
        when(service.findAll()).thenReturn(BudgetData.createBudgets001());

        // When
        MvcResult result = mockMvc.perform(get("/api/budgets"))

        // Then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value("0000001"))
            .andExpect(jsonPath("$[0].amount").value(23000))
            .andReturn()
            ;

        // Convert the response to a list of objects
        String jsonString = result.getResponse().getContentAsString();
        List<Budget> budgets = Arrays.asList(objectMapper.readValue(jsonString, Budget[].class));

        assertFalse(budgets.isEmpty());
        assertEquals(1, budgets.size());
        assertEquals("0000001", budgets.get(0).getId());
        assertEquals(23000, budgets.get(0).getAmount());

        verify(service).findAll();
    } 

    // To test the endpoint post
    @Test
    void postSaveTest() throws Exception {

        // Given
        Budget budgetInsert = new Budget(null, 50000);
        when(service.save(any(Budget.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // when
        MvcResult result = mockMvc.perform(post("/api/budgets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(budgetInsert)))

        // then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.amount").value(50000))
            .andReturn()
            ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Budget newBudget = objectMapper.readValue(jsonString, Budget.class);

        assertEquals(50000, newBudget.getAmount());

        verify(service).save(any(Budget.class));
    }

    // To test the endpoint deleteAll
    @Test
    void deleteAllTest() throws Exception {
    
        // When
        mockMvc.perform(delete("/api/budgets"))

        // then
            .andExpect(status().isOk())
            .andExpect(content().string(""))
            ;

        verify(service).deleteAll();
    }

    // To test the method validation
    @Test
    void validationTest() throws Exception {

        // Given
        Budget budgetInsert = new Budget(null, 40);
        
        // when
        mockMvc.perform(post("/api/budgets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(budgetInsert)))
        
        // then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.amount").value("El campo amount must be greater than or equal to 500"))
            ;

        verify(service, never()).save(any(Budget.class));
    }
    
}

package com.alejandro.controlgastos.controllers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.alejandro.controlgastos.TestConfig;
import com.alejandro.controlgastos.data.BudgetData;
import com.alejandro.controlgastos.data.CustomCondition;
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

    private static final String BASE_URL = "/api/budgets";

    private static final String PUT_DELETE_URL = BASE_URL + "/";


    // To test the getBudgets endpoint
    @Test
    void getBudgetsTest () throws Exception {

        // Given
        when(service.findAll()).thenReturn(BudgetData.createBudgets001());

        // When
        MvcResult result = mockMvc.perform(get(BASE_URL))

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

    // To test the save endpoint
    @Test
    void postSaveTest() throws Exception {

        // Given
        Budget budgetInsert = new Budget(null, 50000);
        when(service.save(any(Budget.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // when
        MvcResult result = mockMvc.perform(post(BASE_URL)
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

    // To test the update endpoint when we use an existing id 
    @Test
    void putUpdateExistingIdTest() throws Exception {
    
        // Given
        String idToUpdate = "0000001";
        Budget budgetToUpdate = new Budget(null, 600);
        when(service.update(anyString(), any(Budget.class))).thenAnswer(invocation -> Optional.of(invocation.getArgument(1)));

        // When
        MvcResult result = mockMvc.perform(put(PUT_DELETE_URL + idToUpdate)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(budgetToUpdate)))

        // then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.amount").value(600))
            .andReturn()
        ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Budget newBudget = objectMapper.readValue(jsonString, Budget.class);

        assertEquals(600, newBudget.getAmount());

        verify(service).update(argThat(new CustomCondition(BudgetData.idsValid, true)), any(Budget.class));
    }

    // To test the update endpoint when we use an inexisting id 
    @Test
    void putUpdateInexistingIdTest() throws Exception {
    
        // Given
        String idToUpdate = "9999999";
        Budget budgetToUpdate = new Budget(null, 600);
        when(service.update(anyString(), any(Budget.class))).thenReturn(Optional.empty());

        // When
        mockMvc.perform(put(PUT_DELETE_URL + idToUpdate)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(budgetToUpdate)))

        // then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(service).update(argThat(new CustomCondition(BudgetData.idsValid, false)), any(Budget.class));
    }

    // To test the deleteAll endpoint
    @Test
    void deleteAllTest() throws Exception {
    
        // When
        mockMvc.perform(delete(BASE_URL))

        // then
            .andExpect(status().isOk())
            .andExpect(content().string(""))
            ;

        verify(service).deleteAll();
    }

    // To test the validation method
    @Test
    void validationTest() throws Exception {

        // Given
        Budget budgetInsert = new Budget(null, 40);
        
        // when
        mockMvc.perform(post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(budgetInsert)))
        
        // then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.amount").value("The amount field must be equal to or greater than 500"))
            ;

        verify(service, never()).save(any(Budget.class));
    }
    
}

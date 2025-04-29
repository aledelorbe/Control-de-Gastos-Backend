package com.alejandro.controlgastos.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
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
import com.alejandro.controlgastos.data.CustomCondition;
import com.alejandro.controlgastos.data.ExpenseData;
import com.alejandro.controlgastos.entities.Expense;
import com.alejandro.controlgastos.services.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ExpenseController.class)
@Import(TestConfig.class)
class ExpenseControllerTest {

    // To inject the dependency that allows for mocking HTTP requests
    @Autowired
    private MockMvc mockMvc;
 
    // To inject the dependency that represents the service to mock
    @Autowired
    private ExpenseService service; 

    @Autowired
    private ObjectMapper objectMapper;

    // To test the enpoint getExpenses
    @Test
    void testGetExpenses () throws Exception {

        // Given
        when(service.findAll()).thenReturn(ExpenseData.createExpenses001());

        // When
        MvcResult result = mockMvc.perform(get("/api/expenses"))

        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].id").value("0000001"))
        .andExpect(jsonPath("$[0].name").value("Netflix"))
        .andExpect(jsonPath("$[0].amount").value(400))
        .andExpect(jsonPath("$[0].category").value("Suscripciones"))
        .andExpect(jsonPath("$[0].createdAt").value("2025-04-25T14:30:00"))
        .andReturn()
        ;

        // Convert the response to a list of objects
        String jsonString = result.getResponse().getContentAsString();
        List<Expense> expenses = Arrays.asList(objectMapper.readValue(jsonString, Expense[].class));

        assertFalse(expenses.isEmpty());
        assertEquals(4, expenses.size());
        assertEquals("0000001", expenses.get(0).getId());
        assertEquals("Netflix", expenses.get(0).getName());
        assertEquals(400, expenses.get(0).getAmount());
        assertEquals("Suscripciones", expenses.get(0).getCategory());
        assertEquals(LocalDateTime.of(2025, 4, 25, 14, 30), expenses.get(0).getCreatedAt());

        verify(service).findAll();
    } 

    // To test the enpoint post
    @Test
    void testPostSave() throws Exception {

        // Given
        Expense expenseInsert = new Expense(null, "Frappe", 50, "Diversión", LocalDateTime.of(2025, 4, 28, 18, 15));
        when(service.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // when
        MvcResult result = mockMvc.perform(post("/api/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(expenseInsert)))

        // then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Frappe"))
            .andExpect(jsonPath("$.amount").value(50))
            .andExpect(jsonPath("$.category").value("Diversión"))
            .andExpect(jsonPath("$.createdAt").value("2025-04-28T18:15:00"))
            .andReturn()
            ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Expense newExpense = objectMapper.readValue(jsonString, Expense.class);

        assertEquals("Frappe", newExpense.getName());
        assertEquals(50, newExpense.getAmount());
        assertEquals("Diversión", newExpense.getCategory());
        assertEquals(LocalDateTime.of(2025, 4, 28, 18, 15), newExpense.getCreatedAt());

        verify(service).save(any(Expense.class));
    }

    // To test the enpoint update when we use an existing id 
    @Test
    void testPutUpdateExistingId() throws Exception {
    
        // Given
        String idToUpdate = "0000002";
        Expense expenseToUpdate = new Expense(null, "veterinario", 250, "Salud", LocalDateTime.of(2025, 4, 28, 18, 15));
        when(service.update(anyString(), any(Expense.class))).thenAnswer(invocation -> Optional.of(invocation.getArgument(1)));

        // When
        MvcResult result = mockMvc.perform(put("/api/expenses/" + idToUpdate)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(expenseToUpdate)))

        // then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("veterinario"))
            .andExpect(jsonPath("$.amount").value(250))
            .andExpect(jsonPath("$.category").value("Salud"))
            .andExpect(jsonPath("$.createdAt").value("2025-04-28T18:15:00"))
            .andReturn()
            ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Expense newExpense = objectMapper.readValue(jsonString, Expense.class);

        assertEquals("veterinario", newExpense.getName());
        assertEquals(250, newExpense.getAmount());
        assertEquals("Salud", newExpense.getCategory());
        assertEquals(LocalDateTime.of(2025, 4, 28, 18, 15), newExpense.getCreatedAt());

        verify(service).update(argThat(new CustomCondition(ExpenseData.idsValid, true)), any(Expense.class));
    }

    // To test the enpoint update when we use an inexisting id 
    @Test
    void testPutUpdateInexistingId() throws Exception {
    
        // Given
        String idToUpdate = "0000008";
        Expense expenseToUpdate = new Expense(null, "veterinario", 250, "Salud", LocalDateTime.of(2025, 4, 28, 18, 15));
        when(service.update(anyString(), any(Expense.class))).thenReturn(Optional.empty());

        // When
        mockMvc.perform(put("/api/expenses/" + idToUpdate)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(expenseToUpdate)))

        // then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
            ;

        verify(service).update(argThat(new CustomCondition(ExpenseData.idsValid, false)), any(Expense.class));
    }

    // To test the enpoint delete when we use an existing id 
    @Test
    void testDeleteExistingId() throws Exception {
    
        // Given
        String idToDelete = "0000001";
        when(service.deleteById(anyString())).thenReturn(Optional.of(ExpenseData.createExpense001()));

        // When
        MvcResult result = mockMvc.perform(delete("/api/expenses/" + idToDelete))

        // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Netflix"))
            .andExpect(jsonPath("$.amount").value(400))
            .andExpect(jsonPath("$.category").value("Suscripciones"))
            .andExpect(jsonPath("$.createdAt").value("2025-04-25T14:30:00"))
            .andReturn()
            ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Expense newExpense = objectMapper.readValue(jsonString, Expense.class);

        assertEquals("Netflix", newExpense.getName());
        assertEquals(400, newExpense.getAmount());
        assertEquals("Suscripciones", newExpense.getCategory());
        assertEquals(LocalDateTime.of(2025, 4, 25, 14, 30), newExpense.getCreatedAt());

        verify(service).deleteById(argThat(new CustomCondition(ExpenseData.idsValid, true)));
    }

    // To test the enpoint delete when we use an inexisting id 
    @Test
    void testDeleteInexistingId() throws Exception {
    
        // Given
        String idToDelete = "0000009";
        when(service.deleteById(anyString())).thenReturn(Optional.empty());

        // When
        mockMvc.perform(delete("/api/expenses/" + idToDelete))

        // then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
            ;

        verify(service).deleteById(argThat(new CustomCondition(ExpenseData.idsValid, false)));
    }

    // To test the enpoint deleteAll
    @Test
    void testDeleteAll() throws Exception {
    
        // When
        mockMvc.perform(delete("/api/expenses"))

        // then
            .andExpect(status().isOk())
            .andExpect(content().string(""))
            ;

        verify(service).deleteAll();
    }

    // To test the method validation
    @Test
    void testValidation() throws Exception {

        // Given
        Expense expenseInsert = new Expense(null, "", -26, "", LocalDateTime.of(2050, 4, 28, 18, 15));
        
        // when
        mockMvc.perform(post("/api/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(expenseInsert)))
        
        // then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name").value("El campo name must not be blank"))
            .andExpect(jsonPath("$.amount").value("El campo amount must be greater than or equal to 1"))
            .andExpect(jsonPath("$.category").value("El campo category must not be blank"))
            .andExpect(jsonPath("$.createdAt").value("El campo createdAt must be a date in the past or in the present"))
            ;

        verify(service, never()).save(any(Expense.class));
    }

}

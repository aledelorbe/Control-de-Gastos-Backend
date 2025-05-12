package com.alejandro.controlgastos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alejandro.controlgastos.entities.Expense;
import com.alejandro.controlgastos.repositories.ExpenseRepository;
import com.alejandro.controlgastos.data.CustomCondition;
import com.alejandro.controlgastos.data.ExpenseData;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImpTest {
    
    // To create a mock
    @Mock
    ExpenseRepository repository; 

    // To create a service object with the injection of a mock
    @InjectMocks
    ExpenseServiceImp service;

    // To test the method findAll
    @Test
    void findAllTest() {

        // Given
        when(repository.findAll()).thenReturn(ExpenseData.createExpenses001());

        // when
        List<Expense> expenses = service.findAll();

        // then
        assertNotNull(expenses);
        assertEquals(4, expenses.size());
        assertEquals("0000002", expenses.get(1).getId());
        assertEquals("Pizza", expenses.get(1).getName());
        assertEquals(100, expenses.get(1).getAmount());
        assertEquals("Diversión", expenses.get(1).getCategory());

        verify(repository).findAll();
    }

    // To test the method save
    @Test
    void saveTest() {

        // Given
        Expense expenseInsert = new Expense(null, "Disney +", 200, "Suscripciones", LocalDateTime.of(2025, 4, 25, 14, 30));
        when(repository.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // when
        Expense newExpense = service.save(expenseInsert);
        
        // then
        assertEquals("Disney +", newExpense.getName());
        assertEquals(200, newExpense.getAmount());
        assertEquals("Suscripciones", newExpense.getCategory());
        assertEquals(LocalDateTime.of(2025, 4, 25, 14, 30), newExpense.getCreatedAt());

        verify(repository).save(any(Expense.class));
    }

    // To test the method update when we use an existing id
    @Test
    void updateExistingIdTest() {

        // Given
        String idToUpdate = "0000001";
        Expense expenseToUpdate = new Expense(idToUpdate, "HBO Max", 500, "Suscripciones", LocalDateTime.now());
        when(repository.findById(anyString())).thenReturn(Optional.of(ExpenseData.createExpense001()));
        when(repository.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Optional<Expense> result = service.update(idToUpdate, expenseToUpdate);

        // Then
        assertTrue(result.isPresent());
        assertEquals("HBO Max", result.get().getName());
        assertEquals(500, result.get().getAmount());
        assertEquals("Suscripciones", result.get().getCategory());
        // The event is not possible to test. It might only be with an integration test.

        verify(repository).findById(argThat(new CustomCondition(ExpenseData.idsValid, true)));
        verify(repository).save(any(Expense.class));
    }

    // To test the method update when we use an inexisting id
    @Test
    void updateInexistingIdTest() {

        String idToUpdate = "0000006";
        Expense expenseToUpdate = new Expense(null, "HBO Max", 500, "Suscripciones", LocalDateTime.now());
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // When
        Optional<Expense> result2 = service.update(idToUpdate, expenseToUpdate);

        // Then
        assertFalse(result2.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            result2.orElseThrow();
        });

        verify(repository).findById(argThat(new CustomCondition(ExpenseData.idsValid, false)));
        verify(repository, never()).save(any(Expense.class));
    }

    // To test the method delete when we use an existing id
    @Test
    void deleteExistingIdTest() {

        // Given
        String idToDelete = "0000001";
        when(repository.findById(anyString())).thenReturn(Optional.of(ExpenseData.createExpense001()));

        // When
        Optional<Expense> result = service.deleteById(idToDelete);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Netflix", result.get().getName());
        assertEquals(400, result.get().getAmount());
        assertEquals("Suscripciones", result.get().getCategory());
        assertEquals(LocalDateTime.of(2025, 4, 25, 14, 30), result.get().getCreatedAt());

        verify(repository).findById(argThat(new CustomCondition(ExpenseData.idsValid, true)));
        verify(repository).deleteById(argThat(new CustomCondition(ExpenseData.idsValid, true)));
    }

    // To test the method delete when we use an inexisting id
    @Test
    void deleteInexistingIdTest() {

        // Given
        String idToDelete = "0000009";
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // When
        Optional<Expense> result = service.deleteById(idToDelete);

        // Then
        assertFalse(result.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            result.orElseThrow();
        });

        verify(repository).findById(argThat(new CustomCondition(ExpenseData.idsValid, false)));
        verify(repository, never()).deleteById(argThat(new CustomCondition(ExpenseData.idsValid, false)));
    }

    // To test the method deleteAll
    @Test
    void deleteAllTest() {

        // when
        service.deleteAll();

        // then
        verify(repository).deleteAll();
    }

}

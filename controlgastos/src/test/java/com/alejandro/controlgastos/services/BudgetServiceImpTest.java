package com.alejandro.controlgastos.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alejandro.controlgastos.data.BudgetData;
import com.alejandro.controlgastos.data.CustomCondition;
import com.alejandro.controlgastos.entities.Budget;
import com.alejandro.controlgastos.repositories.BudgetRepository;


@ExtendWith(MockitoExtension.class)
public class BudgetServiceImpTest {
    
    // To create a mock
    @Mock
    BudgetRepository repository;

    // To create a service object with the injection of a mock
    @InjectMocks
    BudgetServiceImp service;


    // To test the findAll method
    @Test
    void findAllTest() {

        // Given
        when(repository.findAll()).thenReturn(BudgetData.createBudgets001());

        // when
        List<Budget> budgets = service.findAll();

        // then
        assertNotNull(budgets);
        assertEquals(1, budgets.size());
        assertEquals("0000001", budgets.get(0).getId());
        assertEquals(23000, budgets.get(0).getAmount());

        verify(repository).findAll();
    }

    // To test the save method
    @Test
    void saveTest() {

        // Given
        Budget budgetInsert = new Budget(null, 50000);
        when(repository.save(any(Budget.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // when
        Budget newBudget = service.save(budgetInsert);
        
        // then
        assertEquals(50000, newBudget.getAmount());

        verify(repository).save(any(Budget.class));
    }

    // To test the update method when we use an existing id
    @Test
    void updateExistingIdTest() {

        // Given
        String idToUpdate = "0000001";
        Budget budgetToUpdate = new Budget(null, 950);
        when(repository.findById(anyString())).thenReturn(Optional.of(BudgetData.createBudget001()));
        when(repository.save(any(Budget.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Optional<Budget> result = service.update(idToUpdate, budgetToUpdate);

        // Then
        assertTrue(result.isPresent());
        assertEquals(950, result.get().getAmount());

        verify(repository).findById(argThat(new CustomCondition(BudgetData.idsValid, true)));
        verify(repository).save(any(Budget.class));
    }

    // To test the update method when we use an inexisting id
    @Test
    void updateInexistingIdTest() {

        String idToUpdate = "99999";
        Budget budgetToUpdate = new Budget(null, 950);
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // When
        Optional<Budget> result2 = service.update(idToUpdate, budgetToUpdate);

        // Then
        assertFalse(result2.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            result2.orElseThrow();
        });

        verify(repository).findById(argThat(new CustomCondition(BudgetData.idsValid, false)));
        verify(repository, never()).save(any(Budget.class));
    }

    // To test the deleteAll method
    @Test
    void deleteAllTest() {

        // when
        service.deleteAll();

        // then
        verify(repository).deleteAll();
    }

}

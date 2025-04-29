package com.alejandro.controlgastos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alejandro.controlgastos.data.BudgetData;
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

    // To test the metod findAll
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

    // To test the metod save
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

    // To test the metod deleteAll
    @Test
    void deleteAllTest() {

        // when
        service.deleteAll();

        // then
        verify(repository).deleteAll();
    }

}

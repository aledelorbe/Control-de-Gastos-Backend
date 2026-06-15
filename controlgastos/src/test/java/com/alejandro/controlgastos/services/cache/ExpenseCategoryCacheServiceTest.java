package com.alejandro.controlgastos.services.cache;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alejandro.controlgastos.data.ExpenseCategoryData;
import com.alejandro.controlgastos.entities.ExpenseCategory;
import com.alejandro.controlgastos.repositories.RedisCacheRepository;
import com.alejandro.controlgastos.services.ExpenseCategoryService;

import com.fasterxml.jackson.core.type.TypeReference;


@ExtendWith(MockitoExtension.class)
class ExpenseCategoryCacheServiceTest {
    
    // To create the mocks
    @Mock
    private ExpenseCategoryService expenseCategoryService;

    @Mock
    private RedisCacheRepository redisCacheRepository;

    // To create a service object with the injection of a mock
    @InjectMocks
    private ExpenseCategoryCacheService expenseCategoryCacheService;

    String keyExpenseCategories = "expenseCategories";


    // To test the getCategories method when the data return from cache with redis
    @Test
    void shouldReturnCategoriesFromCache() {

        // Given
        List<ExpenseCategory> cachedCategories = ExpenseCategoryData.createExpenseCategories001();

        when(redisCacheRepository.get(
                eq(keyExpenseCategories),
                any(TypeReference.class)))
                .thenReturn(cachedCategories);

        // When
        List<ExpenseCategory> result = expenseCategoryCacheService.getExpenseCategories();

        // Then
        assertEquals(cachedCategories, result);

        verify(redisCacheRepository).get(
                eq(keyExpenseCategories),
                any(TypeReference.class));

        verifyNoInteractions(expenseCategoryService);

        verify(redisCacheRepository, never())
                .set(anyString(), any(), anyLong());
    }

    // To test the getCategories method when the data return from db
    @Test
    void shouldReturnCategoriesFromDatabaseAndSaveInCache() {

        // Given
        List<ExpenseCategory> dbCategories = ExpenseCategoryData.createExpenseCategories001();

        when(redisCacheRepository.get(
                eq(keyExpenseCategories),
                any(TypeReference.class)))
                .thenReturn(null);

        when(expenseCategoryService.getExpenseCategoriesFromDb())
                .thenReturn(dbCategories);

        // When
        List<ExpenseCategory> result = expenseCategoryCacheService.getExpenseCategories();

        // Then
        assertEquals(dbCategories, result);

        verify(redisCacheRepository).get(
                eq(keyExpenseCategories),
                any(TypeReference.class));

        verify(expenseCategoryService)
                .getExpenseCategoriesFromDb();

        verify(redisCacheRepository)
                .set(keyExpenseCategories, dbCategories, 15L);
    }

}
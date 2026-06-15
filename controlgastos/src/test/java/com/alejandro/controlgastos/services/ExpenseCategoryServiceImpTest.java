package com.alejandro.controlgastos.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alejandro.controlgastos.data.ExpenseCategoryData;
import com.alejandro.controlgastos.entities.ExpenseCategory;
import com.alejandro.controlgastos.repositories.ExpenseCategoryRepository;


@ExtendWith(MockitoExtension.class)
class ExpenseCategoryServiceImpTest {

    // To create a mock
    @Mock
    ExpenseCategoryRepository activityCategoryRepository;

    // To create a service object with the injection of a mock
    @InjectMocks
    ExpenseCategoryServiceImp expenseCategoryServiceImp;


    // To test the getCategoriesDb method
    @Test
    void getExpenseCategoriesFromDbTest() {

        // Given
        when(activityCategoryRepository.findAll()).thenReturn(ExpenseCategoryData.createExpenseCategories001());

        // when
        List<ExpenseCategory> activityCategories = expenseCategoryServiceImp.getExpenseCategoriesFromDb();

        // then
        assertNotNull(activityCategories);
        assertEquals(2, activityCategories.size());

        assertEquals("0000002", activityCategories.get(1).getId());
        assertEquals("healthy", activityCategories.get(1).getCategoryName());

        verify(activityCategoryRepository).findAll();
    }

}
package com.alejandro.controlgastos.services.cache;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alejandro.controlgastos.entities.ExpenseCategory;
import com.alejandro.controlgastos.repositories.RedisCacheRepository;
import com.alejandro.controlgastos.services.ExpenseCategoryService;
import com.fasterxml.jackson.core.type.TypeReference;


public class ExpenseCategoryCacheService {
    
    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    @Autowired
    private RedisCacheRepository redisCacheRepository;


    public List<ExpenseCategory> getExpenseCategories() {

        String key = "expenseCategories";

        List<ExpenseCategory> cached = redisCacheRepository.get(
            key,
            new TypeReference<List<ExpenseCategory>>() {}
        );

        if (cached != null) {
            return cached;
        }

        List<ExpenseCategory> fromDb = expenseCategoryService.getExpenseCategoriesFromDb();

        redisCacheRepository.set(key, fromDb, 15);

        return fromDb;
    }

}

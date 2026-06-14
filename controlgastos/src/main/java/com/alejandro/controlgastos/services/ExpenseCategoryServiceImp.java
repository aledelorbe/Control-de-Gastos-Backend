package com.alejandro.controlgastos.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.controlgastos.entities.ExpenseCategory;
import com.alejandro.controlgastos.repositories.ExpenseCategoryRepository;


@Service
public class ExpenseCategoryServiceImp implements ExpenseCategoryService {

    // To inject the repository dependency.
    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;


    // To list all expense categories (records) in the collection 'expenseCategories'.
    @Override
    @Transactional(readOnly = true)
    public List<ExpenseCategory> getExpenseCategoriesFromDb() {
        return expenseCategoryRepository.findAll();
    }

}
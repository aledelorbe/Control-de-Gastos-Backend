package com.alejandro.controlgastos.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.controlgastos.entities.ExpenseCategory;
import com.alejandro.controlgastos.services.ExpenseCategoryService;


@RestController // To create an api rest.
@RequestMapping("/api/expense-categories") // To create a base path.
public class ExpenseCategoryController {

    // To Inject the service dependency
    @Autowired
    private ExpenseCategoryService expenseCategoryService;


    // To create an endpoint that allows invocating the getExpenseCategories method
    @GetMapping()
    public List<ExpenseCategory> expenseCategories() {
        return expenseCategoryService.getExpenseCategories();
    }

}
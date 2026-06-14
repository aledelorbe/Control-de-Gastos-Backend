package com.alejandro.controlgastos.services;


import java.util.List;

import com.alejandro.controlgastos.entities.ExpenseCategory;


public interface ExpenseCategoryService {

    // Declaration of methods to use in 'serviceImp' file

    List<ExpenseCategory> getExpenseCategories();

}
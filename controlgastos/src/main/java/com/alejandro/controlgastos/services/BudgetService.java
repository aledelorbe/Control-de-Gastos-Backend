package com.alejandro.controlgastos.services;

import java.util.List;

import com.alejandro.controlgastos.entities.Budget;

public interface BudgetService {

    // Declaration of methods to use in 'serviceImp' file
    
    public List<Budget> findAll();
    
    public Budget save(Budget budget);

    public void deleteAll();
}

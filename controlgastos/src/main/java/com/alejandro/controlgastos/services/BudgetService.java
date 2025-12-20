package com.alejandro.controlgastos.services;

import java.util.List;

import com.alejandro.controlgastos.entities.Budget;

public interface BudgetService {

    // Declaration of methods to use in 'serviceImp' file
    
    List<Budget> findAll();
    
    Budget save(Budget budget);

    void deleteAll();

}

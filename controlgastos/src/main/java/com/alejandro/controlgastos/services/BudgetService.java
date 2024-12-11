package com.alejandro.controlgastos.services;

import java.util.List;

import com.alejandro.controlgastos.entities.Budget;

public interface BudgetService {

    public List<Budget> findAll();
    
    public Budget save(Budget budget);

    public void deleteAll();
}

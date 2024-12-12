package com.alejandro.controlgastos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.controlgastos.entities.Budget;
import com.alejandro.controlgastos.repositories.BudgetRepository;

@Service
public class BudgetServiceImp implements BudgetService {

    // To inject the repository dependency.
    @Autowired
    private BudgetRepository repository;

    // To list all of budgets (records) in the collection 'budgets'.
    // There will be only one
    @Override
    @Transactional(readOnly = true)
    public List<Budget> findAll() {
        return repository.findAll();
    }

    // To save a new budget (the only one) in the db
    @Override
    @Transactional
    public Budget save(Budget budget) {
        return repository.save(budget);
    }

    // To delete information about the budget amount (the only one)
    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}

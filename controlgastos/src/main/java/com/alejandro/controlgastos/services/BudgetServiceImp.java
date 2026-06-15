package com.alejandro.controlgastos.services;


import java.util.List;
import java.util.Optional;

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


    // To list all budgets (records) in the collection 'budgets'.
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

    // To update a specific budget based on its id
    @Override
    @Transactional
    public Optional<Budget> update(String id, Budget budget) {
        // Search for a specific budget 
        Optional<Budget> optionalBudget = repository.findById(id);
        
        // If the budget is present then...
        if( optionalBudget.isPresent() ) {
            // update that record and return an optional value
            Budget budgetDb = optionalBudget.get();

            budgetDb.setAmount(budget.getAmount());

            return Optional.ofNullable(repository.save(budgetDb));
        }

        return optionalBudget;
    }

    // To delete information about the budget amount (the only one)
    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}

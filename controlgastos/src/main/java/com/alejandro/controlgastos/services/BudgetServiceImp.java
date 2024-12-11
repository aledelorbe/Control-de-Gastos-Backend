package com.alejandro.controlgastos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.controlgastos.entities.Budget;
import com.alejandro.controlgastos.repositories.BudgetRepository;

@Service
public class BudgetServiceImp implements BudgetService {

    @Autowired
    private BudgetRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Budget> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Budget save(Budget budget) {
        return repository.save(budget);
    }

    // To delete information about the budget amount
    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}

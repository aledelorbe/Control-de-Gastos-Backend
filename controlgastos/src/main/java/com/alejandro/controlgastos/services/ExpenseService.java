package com.alejandro.controlgastos.services;

import java.util.List;
import java.util.Optional;

import com.alejandro.controlgastos.entities.Expense;


public interface ExpenseService {

    // Declaration of methods to use in 'serviceImp' file

    public List<Expense> findAll();

    public Expense save(Expense expense);

    public Optional<Expense> update(String id, Expense expense);

    public Optional<Expense> deleteById(String id);

    public void deleteAll();
}

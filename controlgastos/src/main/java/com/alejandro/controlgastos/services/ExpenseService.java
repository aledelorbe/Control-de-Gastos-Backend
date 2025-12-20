package com.alejandro.controlgastos.services;

import java.util.List;
import java.util.Optional;

import com.alejandro.controlgastos.dtos.ExpenseUpdateDTO;
import com.alejandro.controlgastos.entities.Expense;


public interface ExpenseService {

    // Declaration of methods to use in 'serviceImp' file

    List<Expense> findAll();

    Expense save(Expense expense);

    Optional<Expense> update(String id, ExpenseUpdateDTO expense);

    Optional<Expense> deleteById(String id);

    void deleteAll();

}

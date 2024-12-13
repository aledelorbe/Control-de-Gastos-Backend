package com.alejandro.controlgastos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.controlgastos.entities.Expense;
import com.alejandro.controlgastos.repositories.ExpenseRepository;

@Service
public class ExpenseServiceImp implements ExpenseService {

    // To inject the repository dependency.
    @Autowired
    private ExpenseRepository repository;

    // To list all of expenses (records) in the collection 'expenses'.
    @Override
    @Transactional(readOnly = true)
    public List<Expense> findAll() {
        return repository.findAll();
    }

    // To save a new expense in the db
    @Override
    @Transactional
    public Expense save(Expense expense) {
        return repository.save(expense);
    }

    // To update a specific expense based on its id
    @Override
    @Transactional
    public Optional<Expense> update(String id, Expense expense) {
        // Find a specific expense
        Optional<Expense> optionalExpense = repository.findById(id);

        // If the expense is present then...
        if (optionalExpense.isPresent()) {
            // update that record and return an optional value
            Expense expenseDb = optionalExpense.get();

            expenseDb.setName(expense.getName());
            expenseDb.setAmount(expense.getAmount());
            expenseDb.setCategory(expense.getCategory());
            // The attribute: createdAt doesnt update.
            // The attribute: updatedAt is updated in 

            return Optional.ofNullable(repository.save(expenseDb));
        }

        return optionalExpense;
    }

    // To delete a specific expense based on its id
    @Override
    @Transactional
    public Optional<Expense> deleteById(String id) {
        // Find a specific expense
        Optional<Expense> optionalExpense = repository.findById(id);

        // If the expense is present then delete that expense
        optionalExpense.ifPresent(expenseDb -> {
            repository.deleteById(id);
        });

        return optionalExpense;
    }

    // To delete all of expenses
    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

}

package com.alejandro.controlgastos.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.controlgastos.entities.Budget;
import com.alejandro.controlgastos.services.BudgetService;

import jakarta.validation.Valid;


@RestController // To create an api rest.
@RequestMapping("/api/budgets") // To create a base path.
public class BudgetController {

    // To Inject the service dependency
    @Autowired
    private BudgetService budgetService;


    // To create an endpoint that allows invocating the findAll method
    @GetMapping()
    public List<Budget> budgets() {
        return budgetService.findAll();
    }

    // To create an endpoint that allows invocating the save method.
    // The annotation called 'RequestBody' allows receiving data of a client
    @PostMapping()
    public ResponseEntity<?> saveBudget(@Valid @RequestBody Budget budget, BindingResult result) {
        // To handle the obligations of object attributes
        if( result.hasFieldErrors() ){
            return validation(result);
        } 

        // When a new budget is created to respond return the same budget
        Budget newBudget = budgetService.save(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBudget);
    }

    // To create an endpoint that allows update all attributes of the budget (the only one) 
    // and return response created with the new budget
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBudget(@Valid @RequestBody Budget budget, BindingResult result, @PathVariable String id) {
        // To handle of obligations of object attributes
        if( result.hasFieldErrors() ){
            return validation(result);
        } 
        
        // Find specific budget and if it's present then return specific budget
        Optional<Budget> optionalBudget = budgetService.update(id, budget);

        if( optionalBudget.isPresent() ) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalBudget.orElseThrow());
        }
        // Else return code response 404 
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows deleting all budgets (the only one)
    // and return response ok
    @DeleteMapping()
    public ResponseEntity<?> deleteAllBudgets() {
        budgetService.deleteAll();
        return ResponseEntity.ok().build();
    }

    // To send a JSON object with messages about the obligations of each object attribute
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), e.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}

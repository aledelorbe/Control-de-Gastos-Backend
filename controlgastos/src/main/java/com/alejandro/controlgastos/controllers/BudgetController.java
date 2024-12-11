package com.alejandro.controlgastos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.controlgastos.entities.Budget;
import com.alejandro.controlgastos.services.BudgetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class BudgetController {

    // To Inject the service dependency
    @Autowired
    private BudgetService service;

    // To create an endpoint that allows invocating the method findAll.
    @GetMapping("/budgets")
    public List<Budget> budgets() {
        return service.findAll();
    }

    // To create an endpoint that allows invocating the method save.
    // The annotation called 'RequestBody' allows receiving data of a client
    @PostMapping("/budget")
    public ResponseEntity<?> saveBudget(@Valid @RequestBody Budget budget, BindingResult result) {
        // To handle of obligations of object attributes
        if( result.hasFieldErrors() ){
            return validation(result);
        } 

        // When a new budget is created to respond return the same budget
        Budget newBudget = service.save(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBudget);
    }

    // To create a endpoint that allows deleting all of budgets
    @DeleteMapping("/budgets")
    public ResponseEntity<?> deleteAllOfActivity() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

    // To send a JSON object with messages about the obligations of object attributes
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    
}

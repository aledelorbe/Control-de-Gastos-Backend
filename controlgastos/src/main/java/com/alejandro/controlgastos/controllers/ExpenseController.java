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

import com.alejandro.controlgastos.entities.Expense;
import com.alejandro.controlgastos.services.ExpenseService;

import jakarta.validation.Valid;


@RestController // To create a api rest.
@RequestMapping("/api/expenses") // To create a base path.
public class ExpenseController {

    // To Inject the service dependency
    @Autowired
    private ExpenseService service;

    // To create an endpoint that allows invocating the method findAll
    @GetMapping()
    public List<Expense> expenses() {
        return service.findAll();
    }

    // To create an endpoint that allows invocating the method save.
    // The annotation called 'RequestBody' allows receiving data of a client
    @PostMapping()
    public ResponseEntity<?> saveExpense(@Valid @RequestBody Expense expense, BindingResult result) {
        // To handle the obligations of object attributes
        if( result.hasFieldErrors() ){
            return validation(result);
        } 

        // When a new expense is created to respond return the same expense
        Expense newExpense = service.save(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    // To create an endpoint that allows update all of atributte values a specific expense based its id.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@Valid @RequestBody Expense expense, BindingResult result, @PathVariable String id) {
        // To handle the obligations of object attributes
        if( result.hasFieldErrors() ){
            return validation(result);
        } 
        
        // Find specific expense and if it's present then return specific expense
        Optional<Expense> optionalExpense = service.update(id, expense);

        if( optionalExpense.isPresent() ) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalExpense.orElseThrow());
        }
        // Else return code response 404 
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows deleting a specific expense based its id.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        // Find specific expense and if it's present then return specific expense
        Optional<Expense> optionalExpense = service.deleteById(id);
        if( optionalExpense.isPresent() ) {
            return ResponseEntity.ok(optionalExpense.orElseThrow());
        }
        // Else return code response 404 
        return ResponseEntity.notFound().build();
    }

    // To create a endpoint that allows deleting all of expenses
    // and return response ok
    @DeleteMapping()
    public ResponseEntity<?> deleteAllOfExpenses() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

    // To send a JSON object with messages about the obligations of each object attribute
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}

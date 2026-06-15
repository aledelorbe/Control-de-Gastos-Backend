package com.alejandro.controlgastos.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.alejandro.controlgastos.entities.Budget;


// The interface called 'MongoRepository' allows the use of crud operations
public interface BudgetRepository extends MongoRepository<Budget, String> {

} 

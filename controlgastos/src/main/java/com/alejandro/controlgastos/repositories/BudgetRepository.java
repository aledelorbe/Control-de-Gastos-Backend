package com.alejandro.controlgastos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.controlgastos.entities.Budget;

// The interface called 'MongoRepository' allows the use of crud operations
@Repository
public interface BudgetRepository extends MongoRepository<Budget, String> {

} 

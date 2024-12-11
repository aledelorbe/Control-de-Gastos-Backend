package com.alejandro.controlgastos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.controlgastos.entities.Budget;

@Repository
public interface BudgetRepository extends MongoRepository<Budget, String> {

} 

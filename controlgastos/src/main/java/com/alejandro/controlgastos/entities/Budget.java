package com.alejandro.controlgastos.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;

// To specific the name of collection in mongoDb
// In mongoDb the name of this collection is 'budgets' but in this project 
// the name of this class is 'Budget'
@Document(collection = "budgets")
public class Budget {

    // Mapping of class attributes with collection fields in mongoDb
    @Id
    private String id;
    
    // To obligate this attribute to contain values ​​equal to or greater than five hundread
    // This values is arbitrary.
    @Min(500) 
    private int amount;

    public Budget() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

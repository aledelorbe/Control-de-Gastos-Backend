package com.alejandro.controlgastos.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;

@Document(collection = "budgets")
public class Budget {

    @Id
    private String id;
    
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

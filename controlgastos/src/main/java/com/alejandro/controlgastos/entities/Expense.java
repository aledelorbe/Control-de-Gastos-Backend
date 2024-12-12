package com.alejandro.controlgastos.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// To specific the name of collection in mongoDb
// In mongoDb the name of this collection is 'expenses' but in this project 
// the name of this class is 'Expense'
@Document(collection = "expenses")
public class Expense {

    // Mapping of class attributes with collection fields in mongoDb
    @Id
    private String id;

    private String name;

    private int amount;

    private String category;

    private Date createdAt;

    private Date updateAt;

    public Expense() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    

}

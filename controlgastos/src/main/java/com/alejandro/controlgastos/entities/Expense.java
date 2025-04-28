package com.alejandro.controlgastos.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

// To specific the name of collection in mongoDb
// In mongoDb the name of this collection is 'expenses' but in this project 
// the name of this class is 'Expense'
@Document(collection = "expenses")
public class Expense {

    // Mapping of class attributes with collection fields in mongoDb
    @Id
    private String id;

    @NotBlank // To obligate to this attribute not to empty or blank values.
    private String name;

    // To obligate this attribute to contain values ​​equal to or greater than one
    @Min(1)
    private int amount;

    @NotBlank // To obligate to this attribute not to empty or blank values.
    private String category;

    @Field("created_at") // To specific the name of this attribute in the db.
    @PastOrPresent // To obligate the date to be before today or today
    private LocalDateTime createdAt;

    @Field("updated_at") // To specific the name of this attribute in the db.
    private LocalDateTime updatedAt;

    public Expense() {
    }

    public Expense(String id, @NotBlank String name, @Min(1) int amount, @NotBlank String category,
            @PastOrPresent LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updatedAt;
    }

    public void setUpdateAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}

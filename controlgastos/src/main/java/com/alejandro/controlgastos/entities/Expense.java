package com.alejandro.controlgastos.entities;

import java.time.LocalDateTime;

import com.alejandro.controlgastos.dtos.base.ExpenseBase;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;


// In mongoDb the name of this collection is 'expenses' but in this project
// the name of this class is 'Expense'
@Document(collection = "expenses")
public class Expense extends ExpenseBase {

    // Mapping of class attributes with collection fields in mongoDb
    @Id
    private String id;

    @Field("created_at") // To specific the name of this attribute in the db.
    @PastOrPresent(message = "{PastOrPresent.Expense.createdAt}") // To obligate the date to be before today or today
    @NotNull(message = "{NotNull.Expense.createdAt}")
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

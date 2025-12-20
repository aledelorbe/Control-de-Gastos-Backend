package com.alejandro.controlgastos.dtos.base;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


public abstract class ExpenseBase {

    @NotBlank(message = "{NotBlank.Expense.name}")
    protected String name;

    @Min(value = 1, message = "{Min.Expense.amount}")
    protected int amount;

    @NotBlank(message = "{NotBlank.Expense.category}")
    protected String category;

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
}

package com.alejandro.controlgastos.dtos;

import com.alejandro.controlgastos.dtos.base.ExpenseBase;


public class ExpenseUpdateDTO extends ExpenseBase {

    public ExpenseUpdateDTO(String name, int amount, String category) {
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

}

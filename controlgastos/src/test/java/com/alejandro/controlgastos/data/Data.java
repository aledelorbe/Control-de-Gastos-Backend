package com.alejandro.controlgastos.data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.alejandro.controlgastos.entities.Expense;

// The class that contains the data to be mocked in the service methods
public class Data {
    
    public static final List<String> idsValid = Arrays.asList("0000001", "0000002", "0000003", "0000004");

    public static Expense createExpense001() {
        return new Expense(idsValid.get(0), "Netflix", 400, "Suscripciones", LocalDateTime.of(2025, 4, 25, 14, 30));
    }
    
    public static Expense createExpense002() {
        return new Expense(idsValid.get(1), "Pizza", 100, "Diversión", LocalDateTime.of(20253, 3, 15, 12, 20));
    }

    public static Expense createExpense003() {
        return new Expense(idsValid.get(2), "Doctor", 110, "Salud", LocalDateTime.of(2022, 2, 10, 8, 10));
    }

    public static Expense createExpense004() {
        return new Expense(idsValid.get(3), "Pintura", 950, "Casa", LocalDateTime.of(2021, 1, 07, 07, 00));
    }

    public static List<Expense> createExpenses001() {
        return Arrays.asList(createExpense001(), createExpense002(), createExpense003(), createExpense004());
    }
}

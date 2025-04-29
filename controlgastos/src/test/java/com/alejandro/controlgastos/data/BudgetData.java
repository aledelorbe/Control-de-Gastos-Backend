package com.alejandro.controlgastos.data;

import java.util.Arrays;
import java.util.List;

import com.alejandro.controlgastos.entities.Budget;

// The class that contains the data to be mocked in the service and controller methods
public class BudgetData {
    
    public static final List<String> idsValid = Arrays.asList("0000001");

    public static Budget createBudget001() {
        return new Budget(idsValid.get(0), 23000);
    }
    
    public static List<Budget> createBudgets001() {
        return Arrays.asList(createBudget001());
    }
}

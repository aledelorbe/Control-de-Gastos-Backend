package com.alejandro.controlgastos.data;


import java.util.Arrays;
import java.util.List;

import com.alejandro.controlgastos.entities.ExpenseCategory;


// The class that contains the data to be mocked in the service methods
public class ExpenseCategoryData {

    public static final List<String> idsValid = Arrays.asList("0000001", "0000002");


    public static ExpenseCategory createExpenseCategory001() {
        return new ExpenseCategory(idsValid.get(0), "home");
    }

    public static ExpenseCategory createExpenseCategory002() {
        return new ExpenseCategory(idsValid.get(1), "healthy");
    }

    public static List<ExpenseCategory> createExpenseCategories001() {
        return Arrays.asList(createExpenseCategory001(), createExpenseCategory002());
    }
    
}

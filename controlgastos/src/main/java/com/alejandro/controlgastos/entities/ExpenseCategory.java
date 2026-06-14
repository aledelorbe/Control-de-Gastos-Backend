package com.alejandro.controlgastos.entities;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;


// In mongoDb the name of this collection is 'expense_categories' but in this project
// the name of this class is 'ExpenseCategory'
@Document(collection = "expense_categories")
public class ExpenseCategory {

    // Mapping of class attributes with collection fields in mongoDb
    @Id
    private String id;

    @Field(value = "category_name")
    private String categoryName;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

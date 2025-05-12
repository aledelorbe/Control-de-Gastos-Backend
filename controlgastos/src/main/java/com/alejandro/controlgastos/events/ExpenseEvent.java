package com.alejandro.controlgastos.events;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.alejandro.controlgastos.entities.Expense;

// This class extends the 'AbstractMongoEventListener' class to use lifecycle events.
@Component
public class ExpenseEvent extends AbstractMongoEventListener<Expense> {

    // To do certain actions when an object of the class Expense is saved (created or updated)
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Expense> event) {
        Expense currentExpense = event.getSource(); // get the object that is firing the event.

        // It's a update operation
        if (currentExpense.getId() != null) {

            // Remove the blank in the next attributes and set the date in the 'updateAt' attribute
            currentExpense.setName(currentExpense.getName().trim());
            currentExpense.setUpdateAt(LocalDateTime.now()); 
        } 
        // It's a create operation
        else {

            // In this case, only remove the blanks in the next attributes
            currentExpense.setName(currentExpense.getName().trim());
        }
    }
    
}

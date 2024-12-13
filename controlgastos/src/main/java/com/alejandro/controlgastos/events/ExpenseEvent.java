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

        if (currentExpense.getId() != null) {
            // It's a update operation

            currentExpense.setUpdateAt(LocalDateTime.now()); 
        } else {
            // It's a create operation

            // In this case, do it nothing
        }
    }
}

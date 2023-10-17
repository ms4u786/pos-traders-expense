package com.msm.service.expense.core.factory;


import com.msm.service.expense.core.entity.ExpenseCategoryEntity;

public class RecurringExpenseFactory implements ExpenseFactory {

    @Override
    public Expense createExpense(ExpenseCategoryEntity expenseCategoryEntity, Double expenseAmount) {
        return new RecurringExpense(expenseCategoryEntity, expenseAmount);
    }
}

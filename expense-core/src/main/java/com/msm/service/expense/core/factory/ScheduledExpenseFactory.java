package com.msm.service.expense.core.factory;


import com.msm.service.expense.core.entity.ExpenseCategoryEntity;

public class ScheduledExpenseFactory implements ExpenseFactory {

    @Override
    public Expense createExpense(ExpenseCategoryEntity expenseCategoryEntity, Double expenseAmount) {
        return new ScheduledExpense(expenseCategoryEntity, expenseAmount);
    }
}

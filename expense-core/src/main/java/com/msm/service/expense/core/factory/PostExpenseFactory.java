package com.msm.service.expense.core.factory;


import com.msm.service.expense.core.entity.ExpenseCategoryEntity;

public class PostExpenseFactory implements ExpenseFactory {

    @Override
    public Expense createExpense(ExpenseCategoryEntity expenseCategoryEntity, Double expenseAmount) {
        return new PostExpense(expenseCategoryEntity, expenseAmount);
    }
}

package com.msm.service.expense.core.factory;


import com.msm.service.expense.core.entity.ExpenseCategoryEntity;

public interface ExpenseFactory {

    Expense createExpense(ExpenseCategoryEntity expenseCategoryEntity, Double expenseAmount);

}

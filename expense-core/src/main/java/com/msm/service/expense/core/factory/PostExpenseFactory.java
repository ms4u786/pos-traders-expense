package com.msm.service.expense.core.factory;


import com.msm.service.expense.core.entity.ExpenseCategoryBean;

public class PostExpenseFactory implements ExpenseFactory {

    @Override
    public Expense createExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount) {
        return new PostExpense(expenseCategoryBean, expenseAmount);
    }
}
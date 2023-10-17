package com.msm.service.expense.core.factory;


import com.msm.service.expense.core.entity.ExpenseCategoryBean;

public interface ExpenseFactory {

    Expense createExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount);

}

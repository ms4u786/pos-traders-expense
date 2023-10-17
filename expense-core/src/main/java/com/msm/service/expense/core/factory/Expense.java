package com.msm.service.expense.core.factory;


import com.msm.service.expense.core.entity.ExpenseEntity;

/**
 * Expense Factory Method
 */
public interface Expense {

    ExpenseEntity create();

}

package com.msm.service.expense.core.factory;


import com.msm.service.expense.core.entity.ExpenseBean;

/**
 * Expense Factory Method
 */
public interface Expense {

    ExpenseBean create();

}

package com.msm.service.expense.core.factory;



import com.msm.service.expense.common.util.ExpenseConstants;
import com.msm.service.expense.common.util.ExpenseType;
import com.msm.service.expense.core.entity.ExpenseEntity;
import com.msm.service.expense.core.entity.ExpenseCategoryEntity;

import java.util.Date;

public class PostExpense implements Expense {

    ExpenseCategoryEntity expenseCategoryEntity;
    Double expenseAmount;

    public PostExpense(ExpenseCategoryEntity expenseCategoryEntity, Double expenseAmount){
        this.expenseCategoryEntity = expenseCategoryEntity;
        this.expenseAmount = expenseAmount;
    }

    @Override
    public ExpenseEntity create() {
        return new ExpenseEntity.ExpenseBuilder(new Date(), expenseCategoryEntity, expenseAmount)
                .expenseType(ExpenseType.POSTED.getType())
                .comments("System Generated")
                .createdAt(new Date())
                .systemGenerated(ExpenseConstants.YES)
                .build();
    }
}

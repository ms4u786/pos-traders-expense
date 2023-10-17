package com.msm.service.expense.core.factory;



import com.msm.service.expense.common.util.ExpenseConstants;
import com.msm.service.expense.common.util.ExpenseType;
import com.msm.service.expense.common.util.ScheduleType;
import com.msm.service.expense.core.entity.ExpenseEntity;
import com.msm.service.expense.core.entity.ExpenseCategoryEntity;

import java.util.Date;

public class ScheduledExpense implements Expense {

    ExpenseCategoryEntity expenseCategoryEntity;
    Double expenseAmount;

    public ScheduledExpense(ExpenseCategoryEntity expenseCategoryEntity, Double expenseAmount){
        this.expenseCategoryEntity = expenseCategoryEntity;
        this.expenseAmount = expenseAmount;
    }

    @Override
    public ExpenseEntity create() {
        return new ExpenseEntity.ExpenseBuilder(new Date(), expenseCategoryEntity, expenseAmount)
                .expenseType(ExpenseType.SCHEDULED.getType())
                .recurringType(ScheduleType.ONE_TIME.getType())
                .scheduledAt(new Date())
                .referencedExpenseId(0)
                .systemGenerated(ExpenseConstants.YES)
                .build();
    }
}

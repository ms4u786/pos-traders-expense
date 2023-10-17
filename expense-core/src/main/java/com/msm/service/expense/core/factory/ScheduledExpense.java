package com.msm.service.expense.core.factory;



import com.msm.service.expense.common.util.ExpenseConstants;
import com.msm.service.expense.common.util.ExpenseType;
import com.msm.service.expense.common.util.ScheduleType;
import com.msm.service.expense.core.entity.ExpenseBean;
import com.msm.service.expense.core.entity.ExpenseCategoryBean;

import java.util.Date;

public class ScheduledExpense implements Expense {

    ExpenseCategoryBean expenseCategoryBean;
    Double expenseAmount;

    public ScheduledExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount){
        this.expenseCategoryBean = expenseCategoryBean;
        this.expenseAmount = expenseAmount;
    }

    @Override
    public ExpenseBean create() {
        return new ExpenseBean.ExpenseBuilder(new Date(), expenseCategoryBean, expenseAmount)
                .expenseType(ExpenseType.SCHEDULED.getType())
                .recurringType(ScheduleType.ONE_TIME.getType())
                .scheduledAt(new Date())
                .referencedExpenseId(0)
                .systemGenerated(ExpenseConstants.YES)
                .build();
    }
}

package com.msm.service.expense.core.factory;



import com.msm.service.expense.common.util.ExpenseConstants;
import com.msm.service.expense.common.util.ExpenseType;
import com.msm.service.expense.core.entity.ExpenseBean;
import com.msm.service.expense.core.entity.ExpenseCategoryBean;

import java.util.Date;

public class PostExpense implements Expense {

    ExpenseCategoryBean expenseCategoryBean;
    Double expenseAmount;

    public PostExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount){
        this.expenseCategoryBean = expenseCategoryBean;
        this.expenseAmount = expenseAmount;
    }

    @Override
    public ExpenseBean create() {
        return new ExpenseBean.ExpenseBuilder(new Date(), expenseCategoryBean, expenseAmount)
                .expenseType(ExpenseType.POSTED.getType())
                .comments("System Generated")
                .createdAt(new Date())
                .systemGenerated(ExpenseConstants.YES)
                .build();
    }
}

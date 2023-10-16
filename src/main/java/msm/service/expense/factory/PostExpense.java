package msm.service.expense.factory;

import msm.service.expense.beans.ExpenseBean;
import msm.service.expense.beans.ExpenseCategoryBean;
import msm.service.expense.util.ExpenseConstants;
import msm.service.expense.util.ExpenseType;

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

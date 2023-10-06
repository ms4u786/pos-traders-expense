package pos.traders.expense.postradersexpense.factory;

import pos.traders.expense.postradersexpense.beans.ExpenseBean;
import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;
import pos.traders.expense.postradersexpense.util.ExpenseConstants;
import pos.traders.expense.postradersexpense.util.ExpenseType;

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

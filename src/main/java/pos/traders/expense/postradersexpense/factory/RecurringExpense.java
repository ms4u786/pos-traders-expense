package pos.traders.expense.postradersexpense.factory;

import pos.traders.expense.postradersexpense.beans.ExpenseBean;
import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;
import pos.traders.expense.postradersexpense.util.ExpenseConstants;
import pos.traders.expense.postradersexpense.util.ExpenseType;
import pos.traders.expense.postradersexpense.util.RecurringType;
import pos.traders.expense.postradersexpense.util.ScheduleType;

import java.util.Date;

public class RecurringExpense implements Expense {

    ExpenseCategoryBean expenseCategoryBean;
    Double expenseAmount;

    public RecurringExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount){
        this.expenseCategoryBean = expenseCategoryBean;
        this.expenseAmount = expenseAmount;
    }
    
    @Override
    public ExpenseBean create() {
        return new ExpenseBean.ExpenseBuilder(new Date(), expenseCategoryBean, expenseAmount)
                .expenseType(ExpenseType.SCHEDULED.getType())
                .scheduledType(ScheduleType.RECURRING.getType())
                .recurringType(RecurringType.DAILY.getType())
                .scheduledAt(new Date())
                .referencedExpenseId(0)
                .systemGenerated(ExpenseConstants.YES)
                .build();
    }

}
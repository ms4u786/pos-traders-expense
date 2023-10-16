package msm.service.expense.factory;

import msm.service.expense.beans.ExpenseBean;
import msm.service.expense.beans.ExpenseCategoryBean;
import msm.service.expense.util.ExpenseConstants;
import msm.service.expense.util.ExpenseType;
import msm.service.expense.util.RecurringType;
import msm.service.expense.util.ScheduleType;

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

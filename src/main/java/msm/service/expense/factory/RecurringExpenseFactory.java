package msm.service.expense.factory;

import msm.service.expense.beans.ExpenseCategoryBean;

public class RecurringExpenseFactory implements ExpenseFactory {

    @Override
    public Expense createExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount) {
        return new RecurringExpense(expenseCategoryBean, expenseAmount);
    }
}

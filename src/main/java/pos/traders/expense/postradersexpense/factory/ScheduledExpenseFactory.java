package pos.traders.expense.postradersexpense.factory;

import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;

public class ScheduledExpenseFactory implements ExpenseFactory {

    @Override
    public Expense createExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount) {
        return new ScheduledExpense(expenseCategoryBean, expenseAmount);
    }
}

package pos.traders.expense.postradersexpense.factory;

import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;

public class RecurringExpenseFactory implements ExpenseFactory {

    @Override
    public Expense createExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount) {
        return new RecurringExpense(expenseCategoryBean, expenseAmount);
    }
}

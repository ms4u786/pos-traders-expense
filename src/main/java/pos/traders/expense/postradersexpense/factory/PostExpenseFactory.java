package pos.traders.expense.postradersexpense.factory;

import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;

public class PostExpenseFactory implements ExpenseFactory {

    @Override
    public Expense createExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount) {
        return new PostExpense(expenseCategoryBean, expenseAmount);
    }
}

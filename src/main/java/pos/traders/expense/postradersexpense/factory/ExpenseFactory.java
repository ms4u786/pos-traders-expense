package pos.traders.expense.postradersexpense.factory;

import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;

public interface ExpenseFactory {

    Expense createExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount);

}

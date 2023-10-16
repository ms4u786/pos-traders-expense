package msm.service.expense.factory;

import msm.service.expense.beans.ExpenseCategoryBean;

public interface ExpenseFactory {

    Expense createExpense(ExpenseCategoryBean expenseCategoryBean, Double expenseAmount);

}

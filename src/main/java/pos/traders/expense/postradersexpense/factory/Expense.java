package pos.traders.expense.postradersexpense.factory;

import pos.traders.expense.postradersexpense.beans.ExpenseBean;

/**
 * Expense Factory Method
 */
public interface Expense {

    ExpenseBean create();

}

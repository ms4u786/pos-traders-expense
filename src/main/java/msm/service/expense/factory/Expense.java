package msm.service.expense.factory;

import msm.service.expense.beans.ExpenseBean;

/**
 * Expense Factory Method
 */
public interface Expense {

    ExpenseBean create();

}

package pos.traders.expense.postradersexpense.service;

import pos.traders.expense.postradersexpense.dto.ExpenseDto;
import pos.traders.expense.postradersexpense.dto.ServiceResponseDto;

public interface ExpenseService {

    ServiceResponseDto saveExpense(ExpenseDto model);
    ServiceResponseDto getExpensesCurrentMonth();
    ServiceResponseDto getExpensesBetweenDate(String startDate, String endDate, String expenseCategory);
    ServiceResponseDto getExpenseById(Integer expenseId);
    ServiceResponseDto getExpenseByCategoryId(Integer categoryId);
    ServiceResponseDto deleteExpenseById(Integer expenseId);

}

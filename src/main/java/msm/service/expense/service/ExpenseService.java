package msm.service.expense.service;

import msm.service.expense.dto.ExpenseDto;
import msm.service.expense.dto.ServiceResponseDto;

public interface ExpenseService {

    ServiceResponseDto saveExpense(ExpenseDto model);
    ServiceResponseDto getExpensesCurrentMonth();
    ServiceResponseDto getExpensesBetweenDate(String startDate, String endDate, String expenseCategory);
    ServiceResponseDto getExpenseById(Integer expenseId);
    ServiceResponseDto getExpenseByCategoryId(Integer categoryId);
    ServiceResponseDto deleteExpenseById(Integer expenseId);

}

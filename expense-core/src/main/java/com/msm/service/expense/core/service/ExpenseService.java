package com.msm.service.expense.core.service;


import com.msm.service.expense.common.dto.ExpenseDto;
import com.msm.service.expense.common.dto.ServiceResponseDto;

public interface ExpenseService {

    ServiceResponseDto saveExpense(ExpenseDto model);
    ServiceResponseDto getExpensesCurrentMonth();
    ServiceResponseDto getExpensesBetweenDate(String startDate, String endDate, String expenseCategory);
    ServiceResponseDto getExpenseById(Integer expenseId);
    ServiceResponseDto getExpenseByCategoryId(Integer categoryId);
    ServiceResponseDto deleteExpenseById(Integer expenseId);

}

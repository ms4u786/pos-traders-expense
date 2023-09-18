package pos.traders.expense.postradersexpense.service;

import pos.traders.expense.postradersexpense.dto.ExpenseCategoryDto;
import pos.traders.expense.postradersexpense.dto.ServiceResponseDto;

public interface ExpenseCategoryService {

    ServiceResponseDto newExpenseCategory(ExpenseCategoryDto model);
    ServiceResponseDto updateExpenseCategory(ExpenseCategoryDto model);
    ServiceResponseDto getExpenseCategoryById(Integer expenseCategoryId);
    ServiceResponseDto getAllExpenseCategories();

}

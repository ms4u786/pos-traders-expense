package msm.service.expense.service;

import msm.service.expense.dto.ExpenseCategoryDto;
import msm.service.expense.dto.ServiceResponseDto;

public interface ExpenseCategoryService {

    ServiceResponseDto newExpenseCategory(ExpenseCategoryDto model);
    ServiceResponseDto updateExpenseCategory(ExpenseCategoryDto model);
    ServiceResponseDto getExpenseCategoryById(Integer expenseCategoryId);
    ServiceResponseDto getAllExpenseCategories();

}

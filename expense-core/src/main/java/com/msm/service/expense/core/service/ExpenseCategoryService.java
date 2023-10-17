package com.msm.service.expense.core.service;


import com.msm.service.expense.common.dto.ExpenseCategoryDto;
import com.msm.service.expense.common.dto.ServiceResponseDto;

public interface ExpenseCategoryService {

    ServiceResponseDto newExpenseCategory(ExpenseCategoryDto model);
    ServiceResponseDto updateExpenseCategory(ExpenseCategoryDto model);
    ServiceResponseDto getExpenseCategoryById(Integer expenseCategoryId);
    ServiceResponseDto getAllExpenseCategories();

}

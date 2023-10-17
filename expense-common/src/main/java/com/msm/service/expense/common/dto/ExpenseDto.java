package com.msm.service.expense.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class ExpenseDto {

    Integer expenseId;
    ExpenseCategoryDto expenseCategoryEntity;
    Date expenseDate;
    Time expenseTime;
    Double expenseAmount;
    String comments;
    Date createdAt;
    Date lastUpdatedAt;

}

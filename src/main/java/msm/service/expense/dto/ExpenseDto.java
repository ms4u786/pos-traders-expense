package msm.service.expense.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class ExpenseDto {

    Integer expenseId;
    ExpenseCategoryDto expenseCategoryBean;
    Date expenseDate;
    Time expenseTime;
    Double expenseAmount;
    String comments;
    Date createdAt;
    Date lastUpdatedAt;

}

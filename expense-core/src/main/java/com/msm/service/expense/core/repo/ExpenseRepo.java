package com.msm.service.expense.core.repo;


import com.msm.service.expense.core.entity.ExpenseBean;
import com.msm.service.expense.core.entity.ExpenseCategoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface ExpenseRepo extends JpaRepository<ExpenseBean, Integer> {

    Optional<List<ExpenseBean>> findByExpenseCategoryBeanOrderByExpenseDateDesc(ExpenseCategoryBean expenseCategoryBean);
    Optional<List<ExpenseBean>> findByExpenseDateBetweenOrderByExpenseDateDesc(Date startDate, Date endDate);

    Optional<List<ExpenseBean>> findByExpenseDateBetweenAndExpenseCategoryBeanInOrderByExpenseDateDesc(Date startDate, Date endDate, List<ExpenseCategoryBean> expenseCategories);

    @Query(value = "SELECT * FROM expenses WHERE system_generated='N' AND expense_type = 'S' AND scheduled_type = 'O'", nativeQuery = true)
    Optional<List<ExpenseBean>> fetchScheduledOneTimeExpensesForToday();

    @Query(value = "SELECT * FROM expenses WHERE system_generated='N' AND expense_type = 'S' AND scheduled_type = 'R' AND recurring_type = ?1" +
            "AND (scheduled_max_count = -1 OR scheduled_max_count <> scheduled_current_count) AND DATE(cut_off_date) >= CURDATE()", nativeQuery = true)
    Optional<List<ExpenseBean>> fetchScheduledRecurringTypeExpenses(String recurringType);
}

package pos.traders.expense.postradersexpense.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pos.traders.expense.postradersexpense.beans.ExpenseBean;
import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepo extends JpaRepository<ExpenseBean, Integer> {

    Optional<List<ExpenseBean>> findByExpenseCategoryBeanOrderByExpenseDateDesc(ExpenseCategoryBean expenseCategoryBean);
    Optional<List<ExpenseBean>> findByExpenseDateBetweenOrderByExpenseDateDesc(Date startDate, Date endDate);

    Optional<List<ExpenseBean>> findByExpenseDateBetweenAndExpenseCategoryBeanInOrderByExpenseDateDesc(Date startDate, Date endDate, List<ExpenseCategoryBean> expenseCategories);
}

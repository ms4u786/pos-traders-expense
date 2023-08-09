package pos.traders.expense.postradersexpense.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseCategoryRepo extends JpaRepository<ExpenseCategoryBean, Integer> {

    Optional<ExpenseCategoryBean> findByExpenseCategoryTitle(String expenseCategoryTitle);

    Optional<List<ExpenseCategoryBean>> findByExpenseCategoryIdIn(List<Integer> ids);

}

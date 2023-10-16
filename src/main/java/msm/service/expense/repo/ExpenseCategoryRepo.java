package msm.service.expense.repo;

import msm.service.expense.beans.ExpenseCategoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseCategoryRepo extends JpaRepository<ExpenseCategoryBean, Integer> {

    Optional<ExpenseCategoryBean> findByExpenseCategoryTitle(String expenseCategoryTitle);

    Optional<List<ExpenseCategoryBean>> findByExpenseCategoryIdIn(List<Integer> ids);

}

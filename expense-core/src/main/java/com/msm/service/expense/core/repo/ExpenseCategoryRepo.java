package com.msm.service.expense.core.repo;


import com.msm.service.expense.core.entity.ExpenseCategoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseCategoryRepo extends JpaRepository<ExpenseCategoryBean, Integer> {

    Optional<ExpenseCategoryBean> findByExpenseCategoryTitle(String expenseCategoryTitle);

    Optional<List<ExpenseCategoryBean>> findByExpenseCategoryIdIn(List<Integer> ids);

}

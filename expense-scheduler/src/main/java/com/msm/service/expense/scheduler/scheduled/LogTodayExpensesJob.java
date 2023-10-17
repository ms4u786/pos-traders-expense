package com.msm.service.expense.scheduler.scheduled;


import com.msm.service.expense.core.factory.PostExpenseFactory;
import com.msm.service.expense.core.entity.ExpenseEntity;
import com.msm.service.expense.core.repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LogTodayExpensesJob {

    @Autowired
    ExpenseRepo expenseRepo;


    @Scheduled(fixedRate = 10000)
    public void postExpenses(){

        System.out.println("Job 'LogTodayExpensesJob' initiated!");

        Optional<List<ExpenseEntity>> expensesListOpt = expenseRepo.fetchScheduledOneTimeExpensesForToday();

        if(expensesListOpt.isPresent()){


            expensesListOpt.get().stream().forEach(

                    expenseBean -> {

                        ExpenseEntity newExpenseBean = new PostExpenseFactory().createExpense(expenseBean.getExpenseCategoryEntity(), expenseBean.getExpenseAmount()).create();
                        expenseRepo.save(newExpenseBean);

                    }

            );

        }

        System.out.println("Job 'LogTodayExpensesJob' finished!");
    }

}

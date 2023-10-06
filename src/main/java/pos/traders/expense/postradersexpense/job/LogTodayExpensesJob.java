package pos.traders.expense.postradersexpense.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pos.traders.expense.postradersexpense.beans.ExpenseBean;
import pos.traders.expense.postradersexpense.factory.PostExpenseFactory;
import pos.traders.expense.postradersexpense.repo.ExpenseRepo;

import java.util.List;
import java.util.Optional;

@Component
public class LogTodayExpensesJob {

    @Autowired
    ExpenseRepo expenseRepo;


    @Scheduled(fixedRate = 10000)
    public void postExpenses(){

        System.out.println("Job 'LogTodayExpensesJob' initiated!");

        Optional<List<ExpenseBean>> expensesListOpt = expenseRepo.fetchScheduledOneTimeExpensesForToday();

        if(expensesListOpt.isPresent()){


            expensesListOpt.get().stream().forEach(

                    expenseBean -> {

                        ExpenseBean newExpenseBean = new PostExpenseFactory().createExpense(expenseBean.getExpenseCategoryBean(), expenseBean.getExpenseAmount()).create();
                        expenseRepo.save(newExpenseBean);

                    }

            );

        }

        System.out.println("Job 'LogTodayExpensesJob' finished!");
    }

}

package msm.service.expense.job;

import msm.service.expense.repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogRecurringDailyExpensesJob {

    @Autowired
    ExpenseRepo expenseRepo;


    @Scheduled(cron = "${cron-expression.recurring.daily}")
    public void postRecurringDailyExpenses(){

        System.out.println("Job 'LogRecurringDailyExpensesJob' initiated!");

        System.out.println("Job 'LogRecurringDailyExpensesJob' finished!");

    }

}

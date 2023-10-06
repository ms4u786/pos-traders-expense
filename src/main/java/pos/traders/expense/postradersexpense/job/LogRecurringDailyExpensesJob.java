package pos.traders.expense.postradersexpense.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pos.traders.expense.postradersexpense.repo.ExpenseRepo;

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

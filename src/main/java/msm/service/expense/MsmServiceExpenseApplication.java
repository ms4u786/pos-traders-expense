package msm.service.expense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsmServiceExpenseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsmServiceExpenseApplication.class, args);
    }

}

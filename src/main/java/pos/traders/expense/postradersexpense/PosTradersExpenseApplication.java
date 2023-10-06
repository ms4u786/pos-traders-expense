package pos.traders.expense.postradersexpense;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import pos.traders.expense.postradersexpense.dto.ServiceResponseDto;

@SpringBootApplication
@EnableScheduling
public class PosTradersExpenseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosTradersExpenseApplication.class, args);
    }

}

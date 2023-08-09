package pos.traders.expense.postradersexpense;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pos.traders.expense.postradersexpense.dto.ServiceResponseDto;

@SpringBootApplication
public class PosTradersExpenseApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ServiceResponseDto serviceResponseDto(){
        return new ServiceResponseDto();
    }

    public static void main(String[] args) {
        SpringApplication.run(PosTradersExpenseApplication.class, args);
    }

}

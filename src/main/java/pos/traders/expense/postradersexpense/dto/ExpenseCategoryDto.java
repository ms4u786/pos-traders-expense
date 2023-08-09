package pos.traders.expense.postradersexpense.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
public class ExpenseCategoryDto {

    Integer expenseCategoryId;
    Integer parentCategoryId;
    String expenseCategoryTitle;

}

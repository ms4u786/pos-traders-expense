package pos.traders.expense.postradersexpense.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "expense_categories")
@Getter
@Setter
public class ExpenseCategoryBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expense_category_id", nullable = false)
    Integer expenseCategoryId;

    @Column(name = "parent_category_id")
    Integer parentCategoryId;

    @Column(name = "expense_category_title", nullable = false)
    String expenseCategoryTitle;

    @Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    @Column(name = "last_updated_at")
    Date lastUpdatedAt;

}

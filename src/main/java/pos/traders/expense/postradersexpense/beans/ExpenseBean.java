package pos.traders.expense.postradersexpense.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "expenses")
@Getter
@Setter
public class ExpenseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expense_id")
    Integer expenseId;

    @ManyToOne
    @JoinColumn(name="expense_category_id", nullable=false)
    ExpenseCategoryBean expenseCategoryBean;

    @Column(name = "expense_date")
    Date expenseDate;

    @Column(name = "expense_time")
    Time expenseTime;

    @Column(name = "expense_amount", columnDefinition="Decimal(10,2) default 0.00")
    Double expenseAmount;

    @Column(name = "comments", nullable = true)
    String comments;

    @Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    @Column(name = "last_updated_at")
    Date lastUpdatedAt;
}

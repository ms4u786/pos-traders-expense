package msm.service.expense.beans;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "expenses")
@Data
public class ExpenseBean {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
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

    //Y=Yes, N=No
    @Column(name = "system_generated", columnDefinition="VARCHAR(1) default 'N'")
    String systemGenerated;

    @Column(name = "referenced_expense_id")
    Integer referencedExpenseId;

    //P=Posted, S=Scheduled
    @Column(name = "expense_type", columnDefinition="VARCHAR(1) default 'P'")
    String expenseType;

    //O=One Time, R=Recurring
    @Column(name = "scheduled_type", columnDefinition="VARCHAR(1)")
    String scheduledType;

    //D=Daily, W=Weekly, M=Monthly, Y=Yearly, N=After N Number of Days
    @Column(name = "recurring_type", columnDefinition="VARCHAR(2)")
    String recurringType;

    @Column(name = "scheduled_at")
    Date scheduledAt;

    //-1 or null=Forever
    @Column(name = "scheduled_max_count")
    Integer scheduledMaxCount;

    @Column(name = "scheduled_current_count")
    Integer scheduledCurrentCount;

    @Column(name = "cut_off_date")
    Date cutOffDate;

    @Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    @Column(name = "last_updated_at")
    Date lastUpdatedAt;

    public ExpenseBean(){

    }

    public ExpenseBean(ExpenseBuilder builder){

        this.expenseCategoryBean = builder.expenseCategoryBean;
        this.expenseDate = builder.expenseDate;
        this.expenseTime = builder.expenseTime;
        this.expenseAmount = builder.expenseAmount;
        this.comments = builder.comments;
        this.systemGenerated = builder.systemGenerated;
        this.referencedExpenseId = builder.referencedExpenseId;
        this.expenseType = builder.expenseType;
        this.scheduledType = builder.scheduledType;
        this.recurringType = builder.recurringType;
        this.scheduledAt = builder.scheduledAt;
        this.scheduledMaxCount = builder.scheduledMaxCount;
        this.scheduledCurrentCount = builder.scheduledCurrentCount;
        this.cutOffDate = builder.cutOffDate;
        this.createdAt = builder.createdAt;
        this.lastUpdatedAt = builder.lastUpdatedAt;

    }

    public static class ExpenseBuilder{

        ExpenseCategoryBean expenseCategoryBean;
        Date expenseDate;
        Time expenseTime;
        Double expenseAmount;
        String comments;
        String systemGenerated;
        Integer referencedExpenseId;
        String expenseType;
        String scheduledType;
        String recurringType;
        Date scheduledAt;
        Integer scheduledMaxCount;
        Integer scheduledCurrentCount;
        Date cutOffDate;
        Date createdAt;
        Date lastUpdatedAt;

        public ExpenseBuilder(Date expenseDate, ExpenseCategoryBean expenseCategoryBean, Double expenseAmount){
            this.expenseDate = expenseDate;
            this.expenseCategoryBean = expenseCategoryBean;
            this.expenseAmount = expenseAmount;
        }

        public ExpenseBuilder expenseTime(Time expenseTime){
            this.expenseTime = expenseTime;
            return this;
        }

        public ExpenseBuilder systemGenerated(String systemGenerated){
            this.systemGenerated = systemGenerated;
            return this;
        }

        public ExpenseBuilder comments(String comments){
            this.comments = comments;
            return this;
        }

        public ExpenseBuilder referencedExpenseId(Integer referencedExpenseId){
            this.referencedExpenseId = referencedExpenseId;
            return this;
        }

        public ExpenseBuilder expenseType(String expenseType){
            this.expenseType = expenseType;
            return this;
        }

        public ExpenseBuilder scheduledType(String scheduledType){
            this.scheduledType = scheduledType;
            return this;
        }

        public ExpenseBuilder recurringType(String recurringType){
            this.recurringType = recurringType;
            return this;
        }

        public ExpenseBuilder scheduledAt(Date scheduledAt){
            this.scheduledAt = scheduledAt;
            return this;
        }

        public ExpenseBuilder scheduledMaxCount(Integer scheduledMaxCount){
            this.scheduledMaxCount = scheduledMaxCount;
            return this;
        }

        public ExpenseBuilder scheduledCurrentCount(Integer scheduledCurrentCount){
            this.scheduledCurrentCount = scheduledCurrentCount;
            return this;
        }

        public ExpenseBuilder cutOffDate(Date cutOffDate){
            this.cutOffDate = cutOffDate;
            return this;
        }

        public ExpenseBuilder createdAt(Date createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public ExpenseBuilder lastUpdatedAt(Date lastUpdatedAt){
            this.lastUpdatedAt = lastUpdatedAt;
            return this;
        }

        public ExpenseBean build(){
            return new ExpenseBean(this);
        }

    }
}

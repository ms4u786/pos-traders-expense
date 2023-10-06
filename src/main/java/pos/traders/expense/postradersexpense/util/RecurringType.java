package pos.traders.expense.postradersexpense.util;

public enum RecurringType {

    DAILY("D"), WEEKLY("W"), MONTHLY("M"), YEARLY("Y"), AFTER_N_DAYS("N");

    final String type;

    RecurringType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

}

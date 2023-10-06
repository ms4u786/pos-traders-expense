package pos.traders.expense.postradersexpense.util;

public enum ExpenseType {

    POSTED("P"), SCHEDULED("S");

    final String type;

    ExpenseType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

}

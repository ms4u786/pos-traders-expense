package msm.service.expense.util;

public enum ExpenseServiceResponseCode {

    SUCCESS("SU", "Success"),
    FAIL("FA", "Fail"),
    ERROR("ER", "Error"),
    NoRecord("NR", "NO Record Found!"),
    InValid("IV", "Invalid Request!");

    private final String code;
    private final String description;

    ExpenseServiceResponseCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

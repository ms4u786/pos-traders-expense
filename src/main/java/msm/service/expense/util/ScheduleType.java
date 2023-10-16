package msm.service.expense.util;

public enum ScheduleType {

    ONE_TIME("O"), RECURRING("R");

    final String type;

    ScheduleType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

}

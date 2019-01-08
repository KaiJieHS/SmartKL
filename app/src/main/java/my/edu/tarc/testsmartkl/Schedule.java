package my.edu.tarc.testsmartkl;

public class Schedule {
    private int transportID;
    private String transportType;
    private String transportLine;
    private String transportSchedule;

    public Schedule(String transportSchedule) {
        this.transportSchedule = transportSchedule;
    }

    public String getTransportSchedule() {
        return transportSchedule;
    }

    public void setTransportSchedule(String transportSchedule) {
        this.transportSchedule = transportSchedule;
    }
}

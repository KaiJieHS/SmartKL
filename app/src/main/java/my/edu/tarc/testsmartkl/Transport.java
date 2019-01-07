package my.edu.tarc.testsmartkl;

public class Transport {
    private int transportID;
    private String transportType;
    private String transportLine;
    private String transportSchedule;

    public Transport(String hcID, String hcBranchName, String hcBranchLocation, String hcContactNumber) {
    }

    public Transport(String transportLine) {
        this.transportLine = transportLine;
    }

    public String getTransportLine() {
        return transportLine;
    }

    public void setTransportLine(String transportLine) {
        this.transportLine = transportLine;
    }
}

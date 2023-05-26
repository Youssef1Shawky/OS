package Classes;

public class PCB {
    int ProcessId;
    String ProcessState;
    int pc;
    int minBoundary;
    int maxBoundary;

    public PCB(int id, String state, int pc, int min, int max) {
        this.ProcessId = id;
        this.ProcessState = state;
        this.pc = pc;
        this.minBoundary = min;
        this.maxBoundary = max;
    }

    public int getProcessId() {
        return ProcessId;
    }

    public void setProcessId(int processId) {
        ProcessId = processId;
    }

    public String getProcessState() {
        return ProcessState;
    }

    public void setProcessState(String processState) {
        ProcessState = processState;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getMinBoundary() {
        return minBoundary;
    }

    public void setMinBoundary(int minBoundary) {
        this.minBoundary = minBoundary;
    }

    public int getMaxBoundary() {
        return maxBoundary;
    }

    public void setMaxBoundary(int maxBoundary) {
        this.maxBoundary = maxBoundary;
    }


    @Override
    public String toString() {
        return "PCB{" +
                "ProcessId=" + ProcessId +
                ", ProcessState='" + ProcessState + '\'' +
                ", pc=" + pc +
                ", minBoundary=" + minBoundary +
                ", maxBoundary=" + maxBoundary +
                '}';
    }
}

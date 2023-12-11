package Model;

public class Process {
    private String Name;
    // private String color; // for the bonus
    private int ArrivalTime;
    private int BurstTime;
    private int Priority;
    private int StartingTime;
    private int endTime;

    public Process() {
        Name = "";
        ArrivalTime = -1;
        BurstTime = -1;
        Priority = -1;
    }

    public Process(String name, int arrivalTime, int burstTime, int priority) {
        Name = name;
        ArrivalTime = arrivalTime;
        BurstTime = burstTime;
        Priority = priority;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return BurstTime;
    }

    public void setBurstTime(int burstTime) {
        BurstTime = burstTime;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public int getStartingTime() {
        return StartingTime;
    }

    public void setStartingTime(int startingTime) {
        StartingTime = startingTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void executeOneUnit() {
        if (BurstTime > 0) {
            BurstTime--;
        }
    }

    public boolean isFinished() {
        if (BurstTime == 0) {
            return true;
        }
        return false;
    }
}

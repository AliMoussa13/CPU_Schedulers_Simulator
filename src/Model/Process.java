package Model;

import Controller.SRTF;

import java.awt.*;
import java.util.Vector;

public class Process {
    private String Name;
    private Color Color;
    private int ArrivalTime;
    private int BurstTime;
    private int Priority;
    private int StartingTime;
    private int endTime;
    private Vector<Integer> timeHistory = new Vector<>();

    public Process() {
        Name = "";
        Color = Color.BLACK;
        ArrivalTime = -1;
        BurstTime = -1;
        Priority = -1;
    }

    public Process(String name, Color color, int arrivalTime, int burstTime, int priority) {
        Name = name;
        Color = color;
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

    public Object getColor() {
        return Color;
    }

    public void addHistory(int time) {
        timeHistory.add(time);
    }
    public Vector<Integer> getTimeHistory() {
        return timeHistory;
    }
}

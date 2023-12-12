package Controller;

import Model.Process;

import java.util.*;

public class AG extends ProcessController {
    private Vector<Process> queue = new Vector<>();
    private Vector<Process> logic = new Vector<>();
    private int currentTime = 0;
    private int totalTime = getTotalTime();
    private HashMap<String , Integer> processBurstTime = new HashMap<>();
    private HashMap<String , Integer> endTime = new HashMap<>();
    private HashMap<String , Integer> QunatumTable = new HashMap<>();
    private HashMap<String , Integer> AGFactor = new HashMap<>();
    private int Quantum;
    public AG(int quantum) {
        this.Quantum = quantum;
    }

    @Override
    public void execute() {
        logic.addAll(processes);
        Collections.sort(logic, Comparator.comparingInt(Process::getArrivalTime));
        Process compare = null;

        for(Process process: logic) {
            processBurstTime.put(process.getName(), process.getBurstTime());
            endTime.put(process.getName(), 0);
        }

        
    }

    private static int getAGFactor() {
        Random rand = new Random();
        return rand.nextInt(21);
    }
}

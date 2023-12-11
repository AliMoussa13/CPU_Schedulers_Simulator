package Controller;

import Model.Process;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

public class SRTF extends ProcessController {
    private Vector<Process> queue = new Vector<>();
    private Vector<Process> logic = new Vector<>();
    private int currentTime;
    private int totalTime = getTotalTime();
    private HashMap<String , Integer> processBurstTime = new HashMap<>();

    @Override
    public void execute() {
        logic.addAll(processes);
        Collections.sort(logic, Comparator.comparingInt(Process::getArrivalTime));
        Process compare = null;

        for(Process process: logic) {
            processBurstTime.put(process.getName(), process.getBurstTime());
        }


        currentTime = logic.get(0).getArrivalTime();
        totalTime = getTotalTime();
        Process runningProcess = null;

        queue.addAll(logic);

        while (currentTime < totalTime) {

            runningProcess = getShortestProcess(queue,currentTime);
            if(compare != runningProcess) {
                compare = runningProcess;
            }

            if (runningProcess != null) {
                int updatedBurst = processBurstTime.get(runningProcess.getName());
                updatedBurst--;
                processBurstTime.put(runningProcess.getName(),updatedBurst); // to minus the burst time by 1
                currentTime++;

                if (processBurstTime.get(runningProcess.getName()) == 0) { //no more burst time
                    runningProcess = null;
                }
            } else {
                currentTime++;
            }

            if(sortedProcesses.isEmpty())
            {
                sortedProcesses.add(compare);
            }

            if(sortedProcesses.lastElement() != compare)
            {
                sortedProcesses.add(compare);
            }
        }

        for(Process process : sortedProcesses)
        {
            System.out.println(process.getName());
        }
    }

    private Process getShortestProcess(Vector<Process> processes, int currentTime) {
        Process shortest = null;
        int shortestBurst = Integer.MAX_VALUE;

        for (Process process : processes) {

            if(process.getArrivalTime() <= currentTime) {
                if (processBurstTime.get(process.getName()) < shortestBurst) {
                    if(processBurstTime.get(process.getName()) != 0) {
                        shortest = process;
                        shortestBurst = processBurstTime.get(process.getName());
                    }
                }
            }
        }

        return shortest;
    }
}

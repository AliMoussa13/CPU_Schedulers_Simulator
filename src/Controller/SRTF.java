package Controller;

import Model.Process;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

public class SRTF extends ProcessController {
    private Vector<Process> queue = new Vector<>();
    private Vector<Process> logic = new Vector<>();
    private int currentTime = 0;
    private int totalTime = getTotalTime();
    private HashMap<String , Integer> processBurstTime = new HashMap<>();
    private HashMap<String , Integer> endTime = new HashMap<>();

    @Override
    public void execute() {
        logic.addAll(processes);
        Collections.sort(logic, Comparator.comparingInt(Process::getArrivalTime));
        Process compare = null;

        for(Process process: logic) {
            processBurstTime.put(process.getName(), process.getBurstTime());
            endTime.put(process.getName(), 0);
        }

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
                    endTime.put(runningProcess.getName(),currentTime);
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

        System.out.println("process name     waiting time     turnaround time");
        float avrWaiting = 0, avrTurn = 0;
        for(Process process : sortedProcesses)
        {
            try {
                System.out.println(process.getName() + "               " + (endTime.get(process.getName()) - process.getArrivalTime() - process.getBurstTime()) + "                " + (endTime.get(process.getName()) - process.getArrivalTime()));

                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }

        for(Process process: processes) {
            avrTurn += (endTime.get(process.getName()) - process.getArrivalTime());
            avrWaiting += (endTime.get(process.getName()) - process.getArrivalTime() - process.getBurstTime());
        }

        System.out.println("this is the average waiting time: " + (avrWaiting/getTotalNumber()));
        System.out.println("this is the average turnaround time: " + (avrTurn/getTotalNumber()));
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

package Controller;

import Model.Process;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class SJF extends ProcessController {
    private Vector<Process> queue = new Vector<>();
    private Vector<Process> logic = new Vector<>();
    private int currentTime = 0;
    private Process currentProcess = new Process();



    @Override
    public void execute() {
        logic.addAll(processes);
        Collections.sort(logic, Comparator.comparingInt(Process::getArrivalTime));
        int checkerTime = 0;

        while(sortedProcesses.size() < processes.size()) {
            int counter = 0;
            for(Process process : logic) {
                if(currentTime >= process.getArrivalTime()) {
                    queue.add(process);
                    counter++;
                }
                else
                    break;
            }

            for(int i=0;i<counter;i++)
            {
                logic.remove(0);
            }

            Collections.sort(queue, Comparator.comparingInt(Process::getBurstTime));
            checkerTime += queue.get(0).getBurstTime();

            while(!queue.isEmpty() && checkerTime >= queue.get(0).getBurstTime()) {
                currentProcess = queue.get(0);
                queue.remove(0);
                currentProcess.setStartingTime(currentTime);
                currentProcess.addHistory(currentTime);
                currentTime += currentProcess.getBurstTime();
                currentProcess.addHistory(currentTime);
                currentProcess.setEndTime(currentTime);

                sortedProcesses.add(currentProcess);
            }
        }

        float avrWaiting = 0, avrTurn = 0;
        System.out.println("process name     waiting time     turnaround time");
        for(Process process: sortedProcesses) {
            try {
                System.out.println(process.getName() + "               " + (process.getEndTime()-process.getArrivalTime()-process.getBurstTime()) + "                " + (process.getEndTime()-process.getArrivalTime()));
                avrWaiting +=  (process.getEndTime()-process.getArrivalTime()-process.getBurstTime());
                avrTurn += (process.getEndTime()-process.getArrivalTime());

                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }

        AverageWaitingTime = Double.valueOf (avrWaiting/getTotalNumber());
        AverageTurnAroundTime = Double.valueOf((avrTurn/getTotalNumber()));
        System.out.println("this is the average waiting time: " + AverageWaitingTime);
        System.out.println("this is the average turnaround time: " + AverageTurnAroundTime);
    }
}

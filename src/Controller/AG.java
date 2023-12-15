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
    int currentQuantum = 0;
    int halfCurrentQuantum = 0;
    public AG(int quantum) {
        this.Quantum = quantum;
    }

    @Override
    public void execute() {
        logic.addAll(processes);
        Collections.sort(logic, Comparator.comparingInt(Process::getArrivalTime));
        queue.add(null);
        boolean flag = true;

        for(Process process: logic) {
            processBurstTime.put(process.getName(), process.getBurstTime());
            endTime.put(process.getName(), 0);
            QunatumTable.put(process.getName(),Quantum);
            int AGF = getAGFactor(process);
            AGFactor.put(process.getName(), AGF);
        }

        totalTime = getTotalTime();
        Process runningProcess = null;


        while (currentTime < totalTime) {
            // get smallest AGFactor in current time
            runningProcess = getShortestProcess(logic,currentTime,null);

            if(sortedProcesses.isEmpty()) {
                sortedProcesses.add(runningProcess);
            }

            // in case founded calculated before
            if(flag) {
                runHalfQuantum(runningProcess);
            }
            if(!queue.isEmpty()) {
                if(queue.get(0) != null) {
                    for(int index = 0;index <queue.size();index++) {
                        if(processBurstTime.get(queue.get(index).getName()) == 0) {
                            queue.remove(index);
                            index = 0;
                        }

                    }
                }
            }

            // in case flag = false
            flag = true;
            // if it is still the smallest
            if(runningProcess == getShortestProcess(logic,currentTime,null)) {
                while(runningProcess == getShortestProcess(logic,currentTime,null) && processBurstTime.get(runningProcess.getName()) > 0
                        && currentQuantum > 0) {
                    int updatedBurst = processBurstTime.get(runningProcess.getName());
                    updatedBurst--;
                    processBurstTime.put(runningProcess.getName(),updatedBurst);
                    currentQuantum--;
                    currentTime++;
                }
                updateQuantum(runningProcess,currentQuantum);
                runningProcess.addHistory(currentTime);
                if(currentQuantum == 0) {
                    if(!queue.isEmpty()) {
                        if(queue.get(0) != null) {
                            while (!queue.isEmpty() &&
                                    (queue.get(0) == runningProcess || processBurstTime.get(runningProcess.getName()) == 0)) {
                                queue.remove(0);

                                // If the queue becomes empty, break out of the loop
                                if (queue.isEmpty()) {
                                    break;
                                }
                            }
                            if(!queue.isEmpty()) {
                                if(queue.get(0) != runningProcess && processBurstTime.get(queue.get(0).getName()) != 0) {
                                    queue.add(runningProcess);
                                    runningProcess = queue.get(0);
                                    queue.remove(0);
                                    runHalfQuantum(runningProcess);
                                    if(runningProcess == getShortestProcess(logic,currentTime,null))
                                        flag = false;
                                    else {
                                        updateQuantum(runningProcess,currentQuantum);
                                        queue.add(runningProcess);
                                        runningProcess.addHistory(currentTime);
                                    }
                                }
                            }
                        }
                    }
                    if(queue.isEmpty()) {
                        if(currentTime < totalTime)
                            queue.add(getShortestProcess(logic,currentTime,runningProcess));
                    }
                }
                else if(runningProcess != getShortestProcess(logic,currentTime,null)) {
                    if(queue.get(0) == null) {
                        queue.remove(0);
                    }
                    queue.add(runningProcess);
                }
                endTime.put(runningProcess.getName(),currentTime);
            }
            else {
                updateQuantum(runningProcess,currentQuantum);
                runningProcess.addHistory(currentTime);
            }
            if(!queue.isEmpty()) {
                for(int index = 0;index < queue.size();index++)
                {
                    if(processBurstTime.get(queue.get(index).getName()) == 0) {
                        queue.remove(index);
                        index = 0;
                    }
                }
            }
            else {
                if(processBurstTime.get(runningProcess.getName()) != 0) {
                    updateQuantum(runningProcess,currentQuantum);
                    queue.add(runningProcess);
                }
                runningProcess.addHistory(currentTime);
            }
        }

        for (Process process : processes) {
            if (process.getTimeHistory().size() % 2 != 0) {
                if(process.getTimeHistory().get(process.getTimeHistory().size()-1) == process.getTimeHistory().get(process.getTimeHistory().size()-2))
                    process.removeLastElement();
            }
        }


        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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

        AverageWaitingTime = Double.valueOf (avrWaiting/getTotalNumber());
        AverageWaitingTime = Math.round(AverageWaitingTime * 100.0) / 100.0;
        AverageTurnAroundTime = Double.valueOf((avrTurn/getTotalNumber()));
        AverageTurnAroundTime = Math.round(AverageTurnAroundTime * 100.0) / 100.0;
        System.out.println("this is the average waiting time: " + AverageWaitingTime);
        System.out.println("this is the average turnaround time: " + AverageTurnAroundTime);
    }

    private static int getRandomFactor() {
        Random rand = new Random();
        return rand.nextInt(20);
    }

    private int getAGFactor(Process process) {
        int random = getRandomFactor();
        if(random > 10)
            return (10 + process.getBurstTime() + process.getArrivalTime());
        else if (random < 10)
            return (random + process.getBurstTime() + process.getArrivalTime());
        else if(random == 10)
            return (process.getPriority() + process.getBurstTime() + process.getArrivalTime());
        else
            return 99999;
    }

    private Process getShortestProcess(Vector<Process> processes, int currentTime, Process currentRunningProcess) {
        Process shortest = null;
        int shortestAGFactor = Integer.MAX_VALUE;

        for (Process process : processes) {

            if(process != currentRunningProcess) {
                if(process.getArrivalTime() <= currentTime) {
                    if (AGFactor.get(process.getName()) < shortestAGFactor) {
                        if(processBurstTime.get(process.getName()) != 0) {
                            shortest = process;
                            shortestAGFactor = AGFactor.get(process.getName());
                        }
                    }
                }
            }
        }

        if(currentRunningProcess != null) {
            if(shortest == null) {
                return currentRunningProcess;
            }
        }

        return shortest;
    }

    private void updateQuantum(Process process , int state) {
        int oldQuantum = QunatumTable.get(process.getName()), newQuantum = 0;
        if(state == 0) {
            newQuantum = oldQuantum + ((int) Math.ceil(getQuantumMean() * 0.1));
        }
        else {
            newQuantum = oldQuantum + state;
        }
        QunatumTable.put(process.getName(), newQuantum);

        System.out.println(process.getName() + " got new quantum " + newQuantum);
    }

    private int getQuantumMean() {
        Double quantumMean = 0.0;
        for(Process process : processes) {
            quantumMean += QunatumTable.get(process.getName());
        }
        int quantumMeanInInt = (int)  Math.ceil(quantumMean / getTotalNumber() );
        return quantumMeanInInt;
    }

    private int getHalfQuantum(Process process) {
        int quantum = QunatumTable.get(process.getName());
        quantum = (int) Math.ceil(quantum*0.5);
        return quantum;
    }

    private void runHalfQuantum(Process runningProcess) {
        runningProcess.addHistory(currentTime);

        if(sortedProcesses.lastElement() != runningProcess)
        {
            sortedProcesses.add(runningProcess);
        }

        System.out.println(runningProcess.getName() + " is running at " + currentTime + " with AGF " + AGFactor.get(runningProcess.getName()));

        currentQuantum = QunatumTable.get(runningProcess.getName());
        halfCurrentQuantum = getHalfQuantum(runningProcess);

        if(processBurstTime.get(runningProcess.getName()) >= halfCurrentQuantum) {
            currentTime += halfCurrentQuantum;
            int updatedBurst = processBurstTime.get(runningProcess.getName());
            updatedBurst -= halfCurrentQuantum;
            processBurstTime.put(runningProcess.getName(),updatedBurst);
            currentQuantum -= halfCurrentQuantum;
        }
        else {
            currentTime += processBurstTime.get(runningProcess.getName());
            processBurstTime.put(runningProcess.getName(),0);
            runningProcess.addHistory(currentTime);
        }
        endTime.put(runningProcess.getName(),currentTime);
    }
}

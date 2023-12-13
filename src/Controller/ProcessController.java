package Controller;

import Model.Process;
import java.util.Vector;

public abstract class ProcessController {
    protected Vector<Process> processes = new Vector<>();
    protected Vector<Process> sortedProcesses = new Vector<>();
    protected Double AverageWaitingTime = 0.00;
    protected Double AverageTurnAroundTime = 0.00;

    public void setProcesses(Vector<Process> processes) {
        this.processes = processes;
    }

    public int getTotalTime()
    {
        int counter = 0;
        for(Process process: processes)
            counter += process.getBurstTime();
        return counter;
    }

    public int getTotalNumber()
    {
        int counter = 0;
        for(Process process: processes)
            counter++;
        return counter;
    }

    public abstract void execute();

}

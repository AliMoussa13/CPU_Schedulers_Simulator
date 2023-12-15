package Controller;

import Model.Process;

import java.util.Vector;

public class Controller {
    private ProcessController processController;

    public void setProcessController(ProcessController processController) {
        this.processController = processController;
    }

    public Double getAverageWaitingTime() {
        return processController.getAverageWaitingTime();
    }

    public Double getAverageTurnAroundTime() {
        return processController.getAverageTurnAroundTime();
    }
    public void perform(Vector<Process> processes){
        for (Process process: processes) {
            process.getTimeHistory().clear();
        }

        processController.setProcesses(processes);
        processController.execute();
    }
}

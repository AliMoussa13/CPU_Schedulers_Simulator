package Controller;

import Model.Process;

import java.util.Vector;

public class Controller {
    private ProcessController processController;

    public void setProcessController(ProcessController processController) {
        this.processController = processController;
    }


    public void perform(Vector<Process> processes){
        processController.setProcesses(processes);
        processController.execute();
    }
}

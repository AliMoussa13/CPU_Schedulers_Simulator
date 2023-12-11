package Controller;

public class RR extends ProcessController{

    private int Quantum;
    public RR(int quantum) {
        this.Quantum = quantum;
    }
    @Override
    public void execute() {
        // write the logic for RR in here
        // you can take the processes and put it in a vector of type Process
        // then you sort it according to the arrival time
        // if there is multiple processes at the same time take the smaller one first
        // then minus the burst time of the process by quantum if it is too big
        // then loop until all process's burst time equals to zero
        // then output the queue so we can check if it works properly
    }
}

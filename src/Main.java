import Controller.Controller;
import Controller.SJF;
import Controller.SRTF;
import Model.Process;
import Controller.Priority;

import java.util.Vector;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int number;
        Scanner scanner = new Scanner(System.in);
        Vector<Process> processes = new Vector<>();

        //System.out.println("enter the number of processes");
        // number = scanner.nextInt();

        /*
        for(int i = 0;i < number;i++) {
            Process process = new Process();
            String name;
            int arrival, burst, priority;

            System.out.println("enter the process name");
            name = scanner.next();
            process.setName(name);

            System.out.println("enter the process arrival time");
            arrival = scanner.nextInt();
            process.setArrivalTime(arrival);

            System.out.println("enter the process burst time");
            burst = scanner.nextInt();
            process.setBurstTime(burst);

            System.out.println("enter the process priority number");
            priority = scanner.nextInt();
            process.setPriority(priority);

            processes.add(process);
        }
        */

        Controller controller = new Controller();


        System.out.println("this is SRTF:");
        controller.setProcessController(new SRTF());
        controller.perform(processes);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("this is SJF:");
        controller.setProcessController(new SJF());
        controller.perform(processes);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("this is Priority:");
        controller.setProcessController(new Priority());
        controller.perform(processes);


        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
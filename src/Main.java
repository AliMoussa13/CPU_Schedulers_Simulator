import Controller.Controller;
import Controller.SJF;
import Controller.SRTF;
import Controller.Priority;
import Controller.AG;
import Model.Process;


import java.util.Vector;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // GUI gui = new GUI();
        // theres a problem i cant know how to show the charts in the gui (must be connection isuue with the GUI to the whole code )
        // if u want to use the GUI comment the whole code from 18 to 47

        Controller controller = new Controller();
        Vector<Process> processes = new Vector<>();

        processes.add(new Process("p1","Black",0,17,4));
        processes.add(new Process("p2","Black",3,6,9));
        processes.add(new Process("p3","Black",4,10,3));
        processes.add(new Process("p4","Black",29,4,8));
        // processes.add(new Process("p5","Black",0,9,1));


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


        System.out.println("this is AG:");
        controller.setProcessController(new AG(4));
        controller.perform(processes);
    }
}

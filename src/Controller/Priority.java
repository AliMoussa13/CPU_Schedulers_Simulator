package Controller;

import Model.Process;

import java.util.HashMap;
import java.util.Vector;

public class Priority extends ProcessController {
    private Vector<Process> queue = new Vector<>();
    private Vector<Process> logic = new Vector<>();
    private int currentTime;
    private int totalTime = getTotalTime();
    private HashMap<String , Integer> processBurstTime = new HashMap<>();
    private HashMap<String , Integer> endTime = new HashMap<>();
    @Override
    public void execute() {
        // okay I sent you a link to know how this works
        // here is the link if you lost it:
        // https://www.javatpoint.com/os-preemptive-priority-scheduling

        // if you find the code slow when you run it comment the other classes in main

        // you need to keep track of when the process ends I would recommend when a process ends
        // to put it in the hashmap endTime and when you need to update the burst to update it in hashmap
        // processBurstTime you can use the code of SRTF since it is almost the sam concept but you
        // will have to check the priority

        // I hope this helps if you need help tell me :)

    }
}

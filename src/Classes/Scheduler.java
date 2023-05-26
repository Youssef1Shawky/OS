package Classes;

import Enums.MutexType;

import java.io.IOException;
import java.util.*;

public class Scheduler {
    static Queue<Integer> readyQueue = new LinkedList<>();
    static Queue<Integer> blockedQueue = new LinkedList<>();
    static int timeSlice = 2;
    static TreeMap<Integer, Integer> timeSliceEachProcess = new TreeMap<>();

    public static void addToReadyQueue(Integer processId) {
        readyQueue.add(processId);
    }

    public static void addToBlockedQueue(Integer processId) {
        blockedQueue.add(processId);
    }

    public static void clock() throws IOException {
        int count = blockedQueue.size();
        while (count-- > 0) {
            int processId = blockedQueue.poll();
            String instruction = Memory.getInstruction(processId);
            MutexType mutexType = Parser.getNeededResources(instruction);
            if (!Mutex.occupiedResources.containsKey(mutexType)) {
                readyQueue.add(processId);
                System.out.println("Process " + processId + " is unblocked");
                Memory.changeProcessToReady(processId);
            } else
                blockedQueue.add(processId);
        }
        if (readyQueue.isEmpty())
            return;
        int processId = readyQueue.peek();
        PCB pcb = Memory.getPCB(processId);
        if (timeSliceEachProcess.containsKey(processId) && timeSliceEachProcess.get(processId) >= timeSlice) {
            timeSliceEachProcess.put(processId, 0);
            addToReadyQueue(readyQueue.poll());
            processId = readyQueue.peek();
            pcb = Memory.getPCB(processId);
        }
        timeSliceEachProcess.put(processId, timeSliceEachProcess.getOrDefault(processId, 0) + 1);

        String instruction = Memory.getInstruction(processId);

        System.out.println("Process " + processId + " is running");
        System.out.println("Instruction: " + instruction);
        System.out.println("Ready Queue: " + readyQueue);
        System.out.println("Blocked Queue: " + blockedQueue);
        Memory.print();


        MutexType mutexType = Parser.getNeededResources(instruction);
        if (mutexType != null && Mutex.occupiedResources.containsKey(mutexType)) {
            addToBlockedQueue(readyQueue.poll());
            Memory.changeProcessToBlock(processId);
            System.out.println("Process " + processId + " is blocked");
            timeSliceEachProcess.put(processId, 0);
            return;
        }
        String[] data = instruction.split(" ");
        if (data[0].equals("print")){
            SystemCall.print(Memory.getVariable(data[1],pcb));
            Memory.increasePC(processId);
        }
        else if (data[0].equals("assign")) {
            if (data[2].equals("input")) {
                SystemCall.input(pcb);
                Memory.reWriteInstruction(processId, "assign " + data[1] + " " + Memory.getVariable("input", pcb));
            } else if (data[2].equals("readFile")) {
                SystemCall.readFile(data[3], pcb);
                Memory.reWriteInstruction(processId, "assign " + data[1] + " " + Memory.getVariable("file", pcb));
            } else {
                SystemCall.assign(data[1], data[2], pcb);
                Memory.increasePC(processId);
            }
        } else if (data[0].equals("writeFile")) {
            SystemCall.writeFile(Memory.getVariable(data[1],pcb), Memory.getVariable(data[2], pcb));
            Memory.increasePC(processId);
        } else if (data[0].equals("readFile")) {
            SystemCall.readFile(Memory.getVariable(data[1],pcb), pcb);
            Memory.increasePC(processId);
        } else if (data[0].equals("printFromTo")) {
            SystemCall.printFromTo(Integer.parseInt(Memory.getVariable(data[1], pcb)), Integer.parseInt(
                    Memory.getVariable(data[2], pcb)));
            Memory.increasePC(processId);
        } else if (data[0].equals("semWait")) {
            SystemCall.semWait(MutexType.valueOf(data[1]), pcb);
            Memory.increasePC(processId);
        } else if (data[0].equals("semSignal")) {
            SystemCall.semSignal(MutexType.valueOf(data[1]));
            Memory.increasePC(processId);
        } else if (data[0].equals("input")) {
            SystemCall.input(pcb);
            Memory.increasePC(processId);
        }
        if (Memory.processFinished(processId))
            readyQueue.poll();

    }


}

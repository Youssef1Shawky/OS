package Classes;

import Enums.MutexType;

import java.io.*;
import java.util.Scanner;

public class SystemCall {
    static Memory memory;

    public static void print(String data) {
        System.out.println(data);
    }

    public static void assign(String variableName, String value, PCB pcb) throws IOException {
        Memory.assignVariable(variableName, value, pcb);
    }

    public static void writeFile(String fileName, String data) throws IOException {
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(data);
        fileWriter.close();
    }

    public static void readFile(String fileName, PCB pcb) throws IOException {
        File file = new File(fileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String data = "";
        String line;
        while ((line = bufferedReader.readLine()) != null)
            data += line;
        Memory.assignVariable("file", data, pcb);
    }

    public static void input(PCB pcb) {
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();
        Memory.assignVariable("input", data, pcb);
    }

    public static void printFromTo(int a, int b) {
        for (int i = a; i <= b; i++)
            System.out.println(i);
    }

    public static void semWait(MutexType mutexType, PCB pcb) {
        Mutex.occupiedResources.put(mutexType, pcb.getProcessId());
    }

    public static void semSignal(MutexType mutexType) {
        Mutex.occupiedResources.remove(mutexType);
    }

}

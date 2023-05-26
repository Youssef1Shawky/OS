package Classes;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 100; i++) {
            System.out.println("i =                             " + i);
            if (i == 1)
                addProcess("src/Program_1.txt", 1);
            else if (i == 4)
                addProcess("src/Program_2.txt", 2);
            else if (i == 7) {
                addProcess("src/Program_3.txt", 3);
            }
            Scheduler.clock();
        }
    }

    public static void addProcess(String fileName, int id) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> instructions = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null)
            instructions.add(line);

        System.out.println("Process " + id + " arrived.");
        Memory.addProcess(instructions, id, false);
        Scheduler.addToReadyQueue(id);
    }
}

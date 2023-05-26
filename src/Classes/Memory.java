package Classes;

import java.io.*;
import java.util.ArrayList;


public class Memory {

    public static String[] mem = new String[40];

    public static PCB getPCB(int processId) throws IOException {
        if (mem[0].equals(processId + "")) {
            if (mem[3] == null)
                getFromDisk(processId);
            return new PCB(Integer.parseInt(mem[0]), mem[1], Integer.parseInt(mem[2]), Integer.parseInt(mem[3]), Integer.parseInt(mem[4]));
        }
        if (mem[5].equals(processId + "")) {
            if (mem[8] == null)
                getFromDisk(processId);
            return new PCB(Integer.parseInt(mem[5]), mem[6], Integer.parseInt(mem[7]), Integer.parseInt(mem[8]), Integer.parseInt(mem[9]));
        }
        if (mem[13] == null)
            getFromDisk(processId);
        return new PCB(Integer.parseInt(mem[10]), mem[11], Integer.parseInt(mem[12]), Integer.parseInt(mem[13]), Integer.parseInt(mem[14]));
    }

    public static String getInstruction(int processId) throws IOException {
        return mem[getPc(processId)];
    }

    public static void assignVariable(String variableName, String value, PCB pcb) {
        int max = pcb.maxBoundary;
        for (int i = max; i > max - 3; i--) {
            if (mem[i] != null && !mem[i].equals("null")) {
                String[] arr = mem[i].split(" ");
                if (arr[0].equals(variableName)) {
                    mem[i] = variableName + " " + value;
                    return;
                }
            }
        }
        for (int i = max; i > max - 3; i--) {
            if (mem[i] == null || mem[i].equals("null")) {
                mem[i] = variableName + " " + value;
                return;
            }
        }
        for (int i = max; i > max - 3; i--) {
            String[] arr = mem[i].split(" ");
            if (arr[0].equals("input")) {
                mem[i] = variableName + " " + value;
                return;
            }
        }


    }

    public static String getVariable(String variableName, PCB pcb) {
        int max = pcb.maxBoundary;
        for (int i = max; i > max - 3; i--) {
            String[] arr = mem[i].split(" ");
            if (arr[0].equals(variableName))
                return arr[1];
        }
        return null;
    }

    public static void reWriteInstruction(int processId, String newInstruction) throws IOException {
        int pc = getPc(processId);
        mem[pc] = newInstruction;
    }

    private static int getPc(int processId) throws IOException {
        int pc;
        int min;
        if (mem[0].equals(processId + "")) {
            pc = Integer.parseInt(mem[2]);
            if (mem[3] == null)
                getFromDisk(processId);
            min = Integer.parseInt(mem[3]);
        } else if (mem[5].equals(processId + "")) {
            pc = Integer.parseInt(mem[7]);
            if (mem[8] == null)
                getFromDisk(processId);
            min = Integer.parseInt(mem[8]);
        } else {
            pc = Integer.parseInt(mem[12]);
            if (mem[13] == null)
                getFromDisk(processId);
            min = Integer.parseInt(mem[13]);
        }
        pc += min;
        return pc;
    }

    public static void increasePC(int processId) {
        if (mem[0].equals(processId + "")) {
            int x = Integer.parseInt(mem[2]);
            mem[2] = (++x) + "";
        } else if (mem[5].equals(processId + "")) {
            int x = Integer.parseInt(mem[7]);
            mem[7] = (++x) + "";
        } else {
            int x = Integer.parseInt(mem[12]);
            mem[12] = (++x) + "";
        }
    }

    public static boolean processFinished(int processId) {
        String pc;
        int min, max;
        if (mem[0].equals(processId + "")) {
            pc = mem[2];
            min = Integer.parseInt(mem[3]);
            max = Integer.parseInt(mem[4]);
        } else if (mem[5].equals(processId + "")) {
            pc = mem[7];
            min = Integer.parseInt(mem[8]);
            max = Integer.parseInt(mem[9]);
        } else {
            pc = mem[12];
            min = Integer.parseInt(mem[13]);
            max = Integer.parseInt(mem[14]);
        }
        return Integer.parseInt(pc) > max - min - 3;
    }

    public static void getFromDisk(int processId) throws IOException {
        ArrayList<String> instructions = new ArrayList<>();
        FileReader file = new FileReader("src/disk.txt");
        BufferedReader br = new BufferedReader(file);
        String line;
        while ((line = br.readLine()) != null)
            instructions.add(line);
        System.out.println("Process " + processId + " is loaded from disk");
        addProcess(instructions, processId, true);
    }

    public static void addProcess(ArrayList<String> instructions, int id, boolean isReplace) throws IOException {
        int start = 0;
        if (mem[0] == null) {
            mem[start++] = id + "";
            mem[start++] = "Ready";
            mem[start++] = "0";
        } else if (mem[5] == null) {
            start = 5;
            mem[start++] = id + "";
            mem[start++] = "Ready";
            mem[start++] = "0";
        } else if (mem[10] == null) {
            start = 10;
            mem[start++] = id + "";
            mem[start++] = "Ready";
            mem[start++] = "0";
        }
        int startOfInstructions = 15;
        if (mem[15] == null) {
            for (String instruction : instructions)
                mem[startOfInstructions++] = instruction;
            for (int i = 0; i < 3; i++)
                mem[startOfInstructions++] = null;
            mem[start++] = "15";
            mem[start] = (startOfInstructions - 1) + "";
        } else if (mem[27] == null) {
            startOfInstructions = 27;
            for (String instruction : instructions)
                mem[startOfInstructions++] = instruction;
            if (!isReplace)
                for (int i = 0; i < 3; i++)
                    mem[startOfInstructions++] = null;
            if (mem[0].equals(id + "")) {
                mem[3] = "27";
                mem[4] = (startOfInstructions - 1) + "";
            } else if (mem[5].equals(id + "")) {
                mem[8] = "27";
                mem[9] = (startOfInstructions - 1) + "";
            } else if (mem[10].equals(id + "")) {
                mem[13] = "27";
                mem[14] = (startOfInstructions - 1) + "";
            }
        } else {
            replace(instructions, id, isReplace);
        }

    }

    public static void replace(ArrayList<String> instructions, int processId, boolean isReplace) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        if (mem[3] != null && Integer.parseInt(mem[3]) == 27) {
            for (int i = Integer.parseInt(mem[3]); i <= Integer.parseInt(mem[4]); i++)
                list.add(mem[i]);
            mem[3] = null;
            mem[4] = null;
            System.out.println("Process " + mem[0] + " is replaced by process " + processId);
            System.out.println("Process " + mem[0] + " is added to the disk");
        } else if (mem[8] != null && Integer.parseInt(mem[8]) == 27) {
            for (int i = Integer.parseInt(mem[8]); i <= Integer.parseInt(mem[9]); i++)
                list.add(mem[i]);
            mem[8] = null;
            mem[9] = null;
            System.out.println("Process " + mem[5] + " is replaced by process " + processId);
            System.out.println("Process " + mem[5] + " is added to the disk");
        } else if (mem[13] != null && Integer.parseInt(mem[13]) == 27) {
            for (int i = Integer.parseInt(mem[13]); i <= Integer.parseInt(mem[14]); i++)
                list.add(mem[i]);
            System.out.println("Process " + mem[10] + " is replaced by process " + processId);
            System.out.println("Process " + mem[10] + " is added to the disk");
            mem[13] = null;
            mem[14] = null;
        }

        mem[27] = null;
        addProcess(instructions, processId, isReplace);
//        Memory.print();
        writeOnDisk(list);
//        System.out.println("\n\n\n\n\n");
//        System.out.println(list);
//        System.out.println(instructions);

    }

    public static void changeProcessToBlock(int processId) {
        if (mem[0].equals(processId + ""))
            mem[1] = "Blocked";
        else if (mem[5].equals(processId + "")) {
            mem[6] = "Blocked";
        } else
            mem[11] = "Blocked";


    }

    public static void changeProcessToReady(int processId) {
        if (mem[0].equals(processId + ""))
            mem[1] = "Ready";
        else if (mem[5].equals(processId + "")) {
            mem[6] = "Ready";
        } else
            mem[11] = "Ready";
    }

    public static void writeOnDisk(ArrayList<String> list) throws IOException {
        File file = new File("src/disk.txt");
        file.createNewFile();
        FileWriter myWriter = new FileWriter("src/disk.txt");
        StringBuilder sb = new StringBuilder();
        for (String s : list)
            sb.append(s).append("\n");
        System.out.println("Format of disk.txt: ");
        System.out.println("First " + (list.size() - 3) + " line is instructions of process");
        System.out.println("Last 3 line is variables");
        System.out.println(sb);
        myWriter.write(sb.toString());
        myWriter.close();
    }

    public static void print() {
        System.out.println("Memory: ");
        for (int i = 0; i < mem.length; i++) {
            System.out.println(i + ": " + mem[i]);
        }
//        if (mem[0] != null) {
//            System.out.println("Process ID: " + mem[0]);
//            System.out.println("Process State: " + mem[1]);
//            System.out.println("PC: " + mem[2]);
//            System.out.println("Min: " + mem[3]);
//            System.out.println("Max: " + mem[4]);
//            System.out.println("Instructions: ");
//            if (mem[3] == null)
//                System.out.println("In Disk");
//            for (int i = Integer.parseInt(mem[3]); i <= Integer.parseInt(mem[4]); i++)
//                System.out.println(mem[i]);
//        } else if (mem[5] != null) {
//            System.out.println("Process ID: " + mem[5]);
//            System.out.println("Process State: " + mem[6]);
//            System.out.println("PC: " + mem[7]);
//            System.out.println("Min: " + mem[8]);
//            System.out.println("Max: " + mem[9]);
//            System.out.println("Instructions: ");
//            if (mem[8] == null)
//                System.out.println("In Disk");
//            for (int i = Integer.parseInt(mem[8]); i <= Integer.parseInt(mem[9]); i++)
//                System.out.println(mem[i]);
//        } else if (mem[10] != null) {
//            System.out.println("Process ID: " + mem[10]);
//            System.out.println("Process State: " + mem[11]);
//            System.out.println("PC: " + mem[12]);
//            System.out.println("Min: " + mem[13]);
//            System.out.println("Max: " + mem[14]);
//            System.out.println("Instructions: ");
//            if (mem[13] == null)
//                System.out.println("In Disk");
//            for (int i = Integer.parseInt(mem[13]); i <= Integer.parseInt(mem[14]); i++)
//                System.out.println(mem[i]);
//        }
    }
}

package Classes;

import Enums.MutexType;

import java.io.IOException;

public class Parser {
    public static MutexType getNeededResources(String instruction) {
        String[] data = instruction.split(" ");
        if (data[0].equals("semWait"))
            return MutexType.valueOf(data[1]);
        return null;
    }


}

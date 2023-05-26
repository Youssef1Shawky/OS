package Classes;

import Enums.MutexType;

import java.util.TreeMap;

public class Mutex {

    static TreeMap<MutexType, Integer> occupiedResources = new TreeMap<>();
}

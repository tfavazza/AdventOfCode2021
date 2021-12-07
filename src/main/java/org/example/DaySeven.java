package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static java.lang.Math.*;

public class DaySeven {
    public static int smallestMovesPartOne = Integer.MAX_VALUE;
    public static int smallestMovesPartTwo = Integer.MAX_VALUE;
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\IntelliJ\\AdventOfCode2021\\src\\main\\resources\\DaySeven.txt");
        Pattern delimiter = Pattern.compile(",");
        Scanner sc = new Scanner(file).useDelimiter(delimiter);
        HashMap<Long, Long> crabLocations = new HashMap<>();
        while(sc.hasNextLine()) {
            long crab = Long.parseLong(sc.next());
            crabLocations.put(crab, crabLocations.getOrDefault(crab, 0L) + 1L);
        }
        Set<Long> crabKeys = crabLocations.keySet();
        crabKeys.forEach(key -> calculatePartOneMoves(crabLocations, key));
        System.out.println(smallestMovesPartOne);
        int biggest = toIntExact(crabKeys.stream().max(Long::compare).get());
        for (int i = 0; i <= biggest; i ++) {
            calculatePartTwoMoves(crabLocations, i);
        }
        System.out.println(smallestMovesPartTwo);
    }
    public static void calculatePartOneMoves(HashMap<Long, Long> crabLocations, Long crabKey) {
        AtomicInteger localMoves = new AtomicInteger();
        // for each key value pair
        // distance from given key to key i'm looking at, multiplied by value
        // {16=1, 0=1, 1=2, 2=3, 4=1, 7=1, 14=1}, 5 should be (16 - 5) * 1
        crabLocations.forEach((key, value) -> localMoves.set(localMoves.get() + (abs(key.intValue() - crabKey.intValue()) * value.intValue())));
        if (localMoves.get() < smallestMovesPartOne) {
            smallestMovesPartOne = localMoves.get();
        }
    }
    public static void calculatePartTwoMoves(HashMap<Long, Long> crabLocations, int crabKey) {
        // for each key value pair
        // distance from given key to key I'm looking at is one more fuel than last, multiplied by value
        // {16=1, 0=1, 1=2, 2=3, 4=1, 7=1, 14=1}, 16 should be (16 - 5 = (11 + 10 + 9 + 8 + 7 + 6 + 5 + 4 + 3 + 2 + 1) * 1
        AtomicInteger localMoves = new AtomicInteger();
        crabLocations
                .forEach((key, value) -> localMoves.set(localMoves.get() + (fuelForOne((abs(key.intValue() - crabKey))) * value.intValue())));
        if (localMoves.get() < smallestMovesPartTwo) {
            smallestMovesPartTwo = localMoves.get();
        }
    }

    private static int fuelForOne(int starter) {
        int total = 0;
        for (int i = 0; i < starter; i++) {
            total = total + (starter - i);
        }
        return total;
    }
}

package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DaySix {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\IntelliJ\\AdventOfCode2021\\src\\main\\resources\\DaySix.txt");
        Pattern delimiter = Pattern.compile(",");
        HashMap<Long, Long> fishCounts = new HashMap<>();
        fishCounts.put(0L,0L);
        fishCounts.put(1L,0L);
        fishCounts.put(2L,0L);
        fishCounts.put(3L,0L);
        fishCounts.put(4L,0L);
        fishCounts.put(5L,0L);
        fishCounts.put(6L,0L);
        fishCounts.put(7L,0L);
        fishCounts.put(8L,0L);
        Scanner sc = new Scanner(file).useDelimiter(delimiter);
        while(sc.hasNextLine()) {
            long fish = Long.parseLong(sc.next());
            fishCounts.put(fish, fishCounts.getOrDefault(fish, 0L) + 1L);
        }
        partOne(fishCounts);
        partTwo(fishCounts);
    }
    public static void partOne(HashMap<Long, Long> startingFishCount) {
        HashMap<Long, Long> workingFishCount = new HashMap<>(startingFishCount);
        addFish(workingFishCount, 80);
        System.out.println(workingFishCount.values().stream().reduce(0L, Long::sum));
    }
    public static void partTwo(HashMap<Long, Long> startingFishCount) {
        HashMap<Long, Long> workingFishCount = new HashMap<>(startingFishCount);
        addFish(workingFishCount, 256);
        System.out.println(workingFishCount.values().stream().reduce(0L, Long::sum));
    }

    //So, suppose you have a lanternfish with an internal timer value of 3:
    //
    //    After one day, its internal timer would become 2.
    //    After another day, its internal timer would become 1.
    //    After another day, its internal timer would become 0.
    //    After another day, its internal timer would reset to 6,
    //    and it would create a new lanternfish with an internal timer of 8.
    //    After another day, the first lanternfish would have an internal timer of 5,
    //    and the second lanternfish would have an internal timer of 7.
    public static HashMap<Long, Long> addFish(HashMap<Long, Long> workingFishCount, int counter) {
        if (counter == 0) {
            return workingFishCount;
        }
        // current fish to clear out
        long newFish = workingFishCount.get(0L);

        // move everyone down a peg
        workingFishCount.put(0L, workingFishCount.get(1L));
        workingFishCount.put(1L, workingFishCount.get(2L));
        workingFishCount.put(2L, workingFishCount.get(3L));
        workingFishCount.put(3L, workingFishCount.get(4L));
        workingFishCount.put(4L, workingFishCount.get(5L));
        workingFishCount.put(5L, workingFishCount.get(6L));
        workingFishCount.put(6L, workingFishCount.get(7L));
        workingFishCount.put(7L, workingFishCount.get(8L));

        // special case for 6, can be demoted from 7 or flipped from 0
        workingFishCount.put(6L, workingFishCount.get(6L) + newFish);

        // 8 is brand new
        workingFishCount.put(8L, newFish);
        counter--;
        return addFish(workingFishCount, counter);
    }

}

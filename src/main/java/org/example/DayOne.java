package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DayOne {
    public static void main(String[] args) throws IOException {
        File file = new File("E:\\IntelliJ\\AdventOfCode2021\\src\\main\\resources\\DayOne.txt");
        Scanner sc = new Scanner(file);
        ArrayList<Integer> listOfDepths = new ArrayList<>();

        while (sc.hasNextLine()) {
            listOfDepths.add(sc.nextInt());
        }
        partOne(listOfDepths);
        partTwo(listOfDepths);
    }
    public static void partOne(ArrayList<Integer> listOfDepths) {
        int greaterThanCount = 0;
        for (int i = 1; i < listOfDepths.size(); i++) {
            if (listOfDepths.get(i) > listOfDepths.get(i -1)) {
                greaterThanCount++;
            }
        }
        System.out.println(greaterThanCount);
    }
    public static void partTwo(ArrayList<Integer> listOfDepths) {
        int slidingGreaterThanCount = 0;
        for (int i = 3; i < listOfDepths.size(); i++) {
            int firstMeasure = listOfDepths.get(i-3) + listOfDepths.get(i-2) + listOfDepths.get(i-1);
            int secondMeasure = listOfDepths.get(i-2) + listOfDepths.get(i-1) + listOfDepths.get(i);
            if (firstMeasure < secondMeasure) {
                slidingGreaterThanCount++;
            }
        }
        System.out.println(slidingGreaterThanCount);
    }
}

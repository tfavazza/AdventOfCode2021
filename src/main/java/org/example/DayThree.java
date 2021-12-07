package org.example;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DayThree {
    public static void main(String[] args) throws IOException {
        File file = new File("E:\\IntelliJ\\AdventOfCode2021\\src\\main\\resources\\DayThree.txt");
        Scanner sc = new Scanner(file);
        int[] oneCounts = new int[12];
        Arrays.fill(oneCounts, 0);
        int[] zeroCounts = new int[12];
        Arrays.fill(zeroCounts, 0);
        while(sc.hasNextLine()) {
            String bits = sc.next();
            for (int i = 0; i < oneCounts.length; i++) {
                if (bits.charAt(i) == '1') {
                     oneCounts[i] = oneCounts[i] + 1;
                }
                if (bits.charAt(i) == '0') {
                    zeroCounts[i] = zeroCounts[i] + 1;
                }
            }
        }

        int[] gamma = new int[12];
        Arrays.fill(gamma, 0);
        int[] epsilon = new int[12];
        Arrays.fill(epsilon, 0);
        for (int i = 0; i < epsilon.length; i++) {
            if (oneCounts[i] == zeroCounts[i]) {
                System.out.println("They equalled! at position " + i);
                gamma[i] = 1;
                epsilon[i] = 0;
            }
            else
                if (oneCounts[i] > zeroCounts[i]) {
                gamma[i] = 1;
            } else {
                epsilon[i] = 1;
            }
        }
        int translatedBinaryGamma = Integer.parseInt(Arrays.stream(gamma).mapToObj(String::valueOf).collect(Collectors.joining("")), 2);
        int translatedBinaryEpsilon = Integer.parseInt(Arrays.stream(epsilon).mapToObj(String::valueOf).collect(Collectors.joining("")), 2);
        System.out.println(translatedBinaryGamma +  " and " + translatedBinaryEpsilon); // 2601 1494
        System.out.println(translatedBinaryEpsilon * translatedBinaryGamma);
        partTwo(new Scanner(file));
    }
    public static void partTwo(Scanner scanner) {
        List<String> oxygenAnswer = new ArrayList<>();
        while(scanner.hasNextLine()) {
            oxygenAnswer.add(scanner.nextLine());
        }
        List<String> cO2Answer = List.copyOf(oxygenAnswer);

            List<String> oxygen = checkCurrentList(oxygenAnswer, 0, true); // 7551
            List<String> co2 = checkCurrentList(cO2Answer, 0, false); // 1159

       System.out.println("Answer for part two is is " + Integer.parseInt(oxygen.get(0), 2) * Integer.parseInt(co2.get(0), 2)); // 4375225?
    }
    public static List<String> checkCurrentList(List<String> theList, int location, boolean isOxygen) {
        if (location > theList.get(0).length()) {
            System.out.println("something has gone awry");
            return Collections.emptyList();
        }
        char checkForThisAtLocation = numberCount(theList, location, isOxygen);
            theList = theList
                    .stream()
                    .filter(item -> item.charAt(location) != checkForThisAtLocation)
                    .collect(Collectors.toList());
            if (theList.size() == 1) {
                return theList;
            }
            return checkCurrentList(theList, location+1, isOxygen);
    }

    public static char numberCount(List<String> list, int location, boolean isOxygen) {
        long zeroes = list.stream().filter(item -> item.charAt(location) != '0').count();
        long ones = list.stream().filter(item -> item.charAt(location) != '1').count();
        if (isOxygen) {
            if (zeroes >= ones) {
                return '1';
            } else {
                return '0';
            }
        } else {
            if (zeroes >= ones) {
                return '0';
            } else {
                return '1';
            }
        }
    }
}

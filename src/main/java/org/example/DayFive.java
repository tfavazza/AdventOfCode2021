package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DayFive {
    public static HashMap<String, Integer> ventPointsAndCounts = new HashMap<>();
    public static HashMap<String, Integer> partTwoPositionsAndCounts = new HashMap<>();
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\IntelliJ\\AdventOfCode2021\\src\\main\\resources\\DayFive.txt");
        Pattern delimiter = Pattern.compile(",|\\n+|\\r|( -> )|\\s+");
        Scanner sc = new Scanner(file).useDelimiter(delimiter);
        ArrayList<int[]> ventCoordinates = new ArrayList<>();
        // 0,9 -> 5,9 [0,9,5,9]
        while(sc.hasNextLine()) {
            sc.next();
            int first = Integer.parseInt(sc.next());
            int second = Integer.parseInt(sc.next());
            int third = Integer.parseInt(sc.next());
            int fourth = Integer.parseInt(sc.next());
            int[] pretty = {first,second,third,fourth};
            ventCoordinates.add(pretty);
        }
        partOne(ventCoordinates);
        partTwo(ventCoordinates);
        System.out.println(countRevisited(ventPointsAndCounts));
        System.out.println(countRevisited(partTwoPositionsAndCounts));
    }
    public static void partTwo(List<int[]> ventCoordinates) {
        // diagonal lines also works for horizontal and vertical after filtering. Nice!
        ventCoordinates.forEach(DayFive::countDiagonalLines);
    }
    public static void partOne(List<int[]> ventCoordinates) {
        //determine the number of points where at least two lines overlap
        List<int[]> horizontalAndVerticalMatchingCoordinates = ventCoordinates
                .stream()
                .filter(DayFive::doSetsMatch)
                .collect(Collectors.toList());
        horizontalAndVerticalMatchingCoordinates.forEach(DayFive::countVerticalAndHorizontalLines); //[0,9,5,9]

    }

    public static void countDiagonalLines(int[] coordinate) {
        String startCoordinate = coordinate[0] + "," + coordinate[1]; // [0,9]
        String endCoordinate = coordinate[2] + "," + coordinate[3]; // [5,9]

        // keep track as we go
        List<String> tempList = new ArrayList<>();
        tempList.add(startCoordinate);
        tempList.add(endCoordinate);

        // pick second set of coordinates, reverse direction if needed
        int startX = coordinate[2]; // 5
        int startY = coordinate[3];  // 9
        int fillInX = (coordinate[0] - coordinate[2]);
        int fillInY = (coordinate[1] - coordinate[3]);
        int counter = Math.max(Math.abs(fillInX), Math.abs(fillInY));
        boolean reverseX = fillInX < 0;
        boolean reverseY = fillInY < 0;

        for (int i = 1; i < counter; i++) {
            // assume we're going forward, use reverse or 0 fill to swap out
            int newXPosition = startX + i;
            int newYPosition = startY + i;

            if (fillInX == 0) {
                newXPosition = startX;
            }
            if (reverseX) {
                newXPosition = startX - i;
            }
            if (fillInY == 0) {
                newYPosition = startY;
            }
            if (reverseY) {
                newYPosition = startY - i;
            }
            String newPosition = newXPosition + "," + newYPosition;
            tempList.add(newPosition);
        }
            tempList
                    .forEach(position -> partTwoPositionsAndCounts
                            .put(position, partTwoPositionsAndCounts
                                    .getOrDefault(position, 0) + 1));
        }

    private static boolean doSetsMatch(int[] array) {
        return horizontalMatch(array) || verticalMatch(array);
    }
    private static boolean horizontalMatch(int[] array) {
        return array[0] == array[2];
    }
    private static boolean verticalMatch(int[] array) {
        return array[1] == array[3];
    }
    private static void countVerticalAndHorizontalLines(int[] coordinate) {
        String start = coordinate[0] + "," + coordinate[1]; // [0,9]
        String end  = coordinate[2] + "," + coordinate[3]; // [5,9]
        ventPointsAndCounts.put(start, ventPointsAndCounts.getOrDefault(start, 0) + 1);
        ventPointsAndCounts.put(end, ventPointsAndCounts.getOrDefault(end, 0) + 1);
        int startingCoordinate = 0;
        int unchangedCoordinate = 0;
        int fillIn = 0;
        boolean isHorizontal = false;
        // I hate this duplicated work
        if (horizontalMatch(coordinate)) {
            isHorizontal = true;
            fillIn = coordinate[1] - coordinate[3]; // 5-0 = 5
            unchangedCoordinate = coordinate[2];
            if (fillIn < 0) {
                fillIn = Math.abs(fillIn);
                startingCoordinate = coordinate[1];
            } else {
                startingCoordinate = coordinate[3];
            }
        }
        else {
            fillIn = coordinate[0] - coordinate[2];
            unchangedCoordinate = coordinate[1];
            if (fillIn < 0) {
                fillIn = Math.abs(fillIn);
                startingCoordinate = coordinate[0];
            } else {
                startingCoordinate = coordinate[2];
            }
        }
        List<String> something =
        additionsToMap(fillIn, startingCoordinate, unchangedCoordinate, isHorizontal);
               something.forEach(attTo -> ventPointsAndCounts.put(attTo, ventPointsAndCounts.getOrDefault(attTo, 0) + 1));
    }

    private static List<String> additionsToMap(int fillIn, int startingCoordinate, int unchangedCoordinate, boolean horizontal) {
        List<String> tempList = new ArrayList<>();
        for (int i = 1; i < fillIn; i++) {
            if (horizontal) {
                tempList.add(unchangedCoordinate + "," + (startingCoordinate + i));
            } else {
                tempList.add((startingCoordinate + i) + "," + unchangedCoordinate);
            }
        }
        return tempList;
    }

    private static long countRevisited(HashMap<String, Integer> ventPointsAndCounts) {
        return ventPointsAndCounts.values().stream().filter(value -> value > 1).count();
    }
}

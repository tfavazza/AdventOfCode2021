package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DayTwo {
    public static void main(String[] args) throws IOException {
        File file = new File("E:\\IntelliJ\\AdventOfCode2021\\src\\main\\resources\\DayTwo.txt");
        Scanner sc = new Scanner(file);
        Scanner sc2 = new Scanner(file);
        partOne(sc);
        partTwo(sc2);

    }
    public static void partOne(Scanner sc) {
        int horizontal = 0;
        int depth = 0;
        while (sc.hasNextLine()) {
            String direction = sc.next();
            switch (direction) {
                case "forward":
                    horizontal = horizontal + Integer.parseInt(sc.next());
                    break;
                case "down":
                    depth = depth + Integer.parseInt(sc.next());
                    break;
                case "up":
                    depth = depth - Integer.parseInt(sc.next());
                    break;
            }
        }
        int total = horizontal * depth;
        System.out.println(total);
    }
    public static void partTwo(Scanner sc) {
        int horizontal = 0;
        int depth = 0;
        int aim = 0;
        while (sc.hasNextLine()) {
            String direction = sc.next();
            switch (direction) {
                case "forward":
                    int forwardDirections = Integer.parseInt(sc.next());
                    horizontal = horizontal + forwardDirections;
                    depth = depth + (aim * forwardDirections);
                    break;
                case "down":
                    int downDirections = Integer.parseInt(sc.next());
                    aim = aim + downDirections;
                    break;
                case "up":
                    int upDirections = Integer.parseInt(sc.next());
                    aim = aim - upDirections;
                    break;
            }
        }
        int total = horizontal * depth;
        System.out.println(total);
    }
}

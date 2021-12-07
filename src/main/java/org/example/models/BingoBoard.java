package org.example.models;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BingoBoard {
    int[][] rowOne;
    int[][] rowTwo;
    int[][] rowThree;
    int[][] rowFour;
    int[][] rowFive;

    public List<int[][]> getAllRows() {
        return List.of(rowOne, rowTwo, rowThree, rowFour, rowFive);
    }

    public int[][] getRowOne() {
        return rowOne;
    }

    public void setRowOne(int[][] rowOne) {
        this.rowOne = rowOne;
    }

    public int[][] getRowTwo() {
        return rowTwo;
    }

    public void setRowTwo(int[][] rowTwo) {
        this.rowTwo = rowTwo;
    }

    public int[][] getRowThree() {
        return rowThree;
    }

    public void setRowThree(int[][] rowThree) {
        this.rowThree = rowThree;
    }

    public int[][] getRowFour() {
        return rowFour;
    }

    public void setRowFour(int[][] rowFour) {
        this.rowFour = rowFour;
    }

    public int[][] getRowFive() {
        return rowFive;
    }

    public void setRowFive(int[][] rowFive) {
        this.rowFive = rowFive;
    }

    public boolean hasBingo() {
        // horizontal bingo
        Optional<int[][]> maybeHorizontalBingo = getAllRows().stream().filter(this::horizontalWinner).findFirst();
        if (maybeHorizontalBingo.isPresent()) {
            int[] test = maybeHorizontalBingo
                    .stream()
                    .map(card -> Arrays
                            .stream(card)
                            .mapToInt(takeTwo -> takeTwo[0]).toArray()
                    ).flatMapToInt(Arrays::stream)
                    .toArray();
            return true;
        }
        // vertical bingo
        for (int i = 0; i < rowOne.length; i++) {
            if((rowOne[i][1] + rowTwo[i][1] + rowThree[i][1] + rowFour[i][1] + rowFive[i][1]) == 5) {
                return true;
            }
        }
        return false;

    }
    private boolean horizontalWinner(int[][] row) {
        return Arrays
                .stream(row)
                .map(bingoSpot -> bingoSpot[1])
                .filter(found -> found == 1)
                .count() == 5;
    }
    public int getSumOfCurrentlyUnmarked() {
        return getAllRows()
                .stream()
                .flatMap(row -> Arrays
                        .stream(row)
                        .filter(bingoNumber -> bingoNumber[1] != 1))
                .collect(Collectors.toList())
                .stream()
                .map(number -> number[0])
                .reduce(0, Integer::sum);
    }
}

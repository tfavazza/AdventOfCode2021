package org.example;

import org.example.models.BingoBoard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DayFour {
public static void main(String[] args) throws FileNotFoundException {
    File file = new File("E:\\IntelliJ\\AdventOfCode2021\\src\\main\\resources\\DayFour.txt");
    Pattern comma = Pattern.compile(",|\\n+|\\r");
    Scanner sc = new Scanner(file).useDelimiter(comma);
    ArrayList<String> bingoNumbers = new ArrayList<>();

    while (sc.hasNext() && sc.hasNext(".|[0-9]{2}")) {
        bingoNumbers.add(sc.next());
    }
    sc.next();
    sc.next();

    List<BingoBoard> boardList = new ArrayList<>();
    while(sc.hasNext()) {
        String firstLineMaybe = sc.nextLine();
        if (firstLineMaybe.equals("")) {
            continue;
        }
        BingoBoard board = new BingoBoard();
        board.setRowOne(setARow(firstLineMaybe));
        board.setRowTwo(setARow(sc.nextLine()));
        board.setRowThree(setARow(sc.nextLine()));
        board.setRowFour(setARow(sc.nextLine()));
        board.setRowFive(setARow(sc.nextLine()));
        boardList.add(board);
    }
    List<BingoBoard> otherBoardList = new ArrayList<>(boardList);
    partOne(boardList, bingoNumbers);
    partTwo(otherBoardList, bingoNumbers);
}
public static void partTwo(List<BingoBoard> boardList, ArrayList<String> bingoNumbers) {
    List<BingoBoard> thisRoundsWinners;
    int currentCount = 0;
    String lastCalledNumberWithABingo = "";
    List<BingoBoard> allCurrentWinners = new ArrayList<>(Collections.emptyList());
    for (int i = 0; i < bingoNumbers.size(); i++) {
        int finalI = i;
        boardList.forEach(bingoBoard -> bingoBoard.getAllRows()
                        .forEach(row -> Arrays.stream(row)
                                .filter(bingoBoardNumber -> bingoBoardNumber[0] == Integer.parseInt(bingoNumbers.get(finalI)))
                                .findAny()
                                .ifPresent(markNumber -> markNumber[1] = 1)
                        )
                );
        thisRoundsWinners = boardList.stream().filter(BingoBoard::hasBingo).collect(Collectors.toList());
        allCurrentWinners.addAll(thisRoundsWinners);
        if (allCurrentWinners.size() > currentCount) {
            boardList.removeAll(allCurrentWinners);
            currentCount = allCurrentWinners.size();
            lastCalledNumberWithABingo = bingoNumbers.get(finalI);
        }
        if (boardList.size() == 0) {
            printTheWinner(thisRoundsWinners.get(thisRoundsWinners.size() - 1));
            int winningSum = thisRoundsWinners.get(thisRoundsWinners.size()-1).getSumOfCurrentlyUnmarked();
            System.out.println(lastCalledNumberWithABingo);
            int winningNumber = Integer.parseInt(lastCalledNumberWithABingo);
            System.out.println("The winning number for part two is " + winningNumber * winningSum);
            break;
        }
    }



}

public static void partOne(List<BingoBoard> boardList, ArrayList<String> bingoNumbers) {
    for (int i = 0; i < bingoNumbers.size(); i++) {
        int finalI = i;
        boardList.forEach(bingoBoard -> bingoBoard.getAllRows()
                        .forEach(row -> Arrays.stream(row)
                                .filter(bingoBoardNumber -> bingoBoardNumber[0] == Integer.parseInt(bingoNumbers.get(finalI)))
                                .findAny()
                                .ifPresent(markNumber -> markNumber[1] = 1)
                        )
                );
        List<BingoBoard> aWinnerMaybe = boardList.stream().filter(BingoBoard::hasBingo).collect(Collectors.toList());
        if (!aWinnerMaybe.isEmpty()) {
            BingoBoard aWinner = aWinnerMaybe.get(0);
            printTheWinner(aWinner);
            int sum = aWinner.getSumOfCurrentlyUnmarked();
            System.out.println("winning total for part one is " + sum * Integer.parseInt(bingoNumbers.get(i)));
            break;
        }
    }
}

public static int[][] setARow(String string) {
        return Arrays
                .stream(string.trim().split("\\s+"))
                .map(Integer::parseInt)
                .map(i -> new int[]{i,0})
                .toArray(size -> new int[size][1]);
    }

    public static void printTheWinner(BingoBoard aWinner) {
        System.out.println("It should be bingo board:");
        System.out.println(Arrays.deepToString(aWinner.getRowOne()));
        System.out.println(Arrays.deepToString(aWinner.getRowTwo()));
        System.out.println(Arrays.deepToString(aWinner.getRowThree()));
        System.out.println(Arrays.deepToString(aWinner.getRowFour()));
        System.out.println(Arrays.deepToString(aWinner.getRowFive()));
    }
}

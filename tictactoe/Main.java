package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cells: ");
        var input = scanner.nextLine().toCharArray();
        var ticTacToe = new Board(3, input);
        ticTacToe.printBoard();
        do {
            System.out.print("Enter the coordinates: ");
            try {
                var coordinateY = scanner.nextInt() - 1;
                var coordinateX = 3 - scanner.nextInt();

                if (!areCoordinatesInBound(coordinateX, coordinateY)) {
                    System.out.println("Coordinates should be from 1 to 3!");
                }else if (ticTacToe.isCellOccupied(coordinateX, coordinateY)) {
                    System.out.println("This cell is occupied! Choose another one!");
                }else {
                    ticTacToe.write('X', coordinateX, coordinateY);
                    break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
            }
        } while (true);
        ticTacToe.printBoard();
    }

    private static boolean areCoordinatesInBound(int coordinateX, int coordinateY) {
        return IntStream.range(0, 3).anyMatch((number) -> number == coordinateX) &&
                IntStream.range(0,3).anyMatch((number) -> number == coordinateY);
    }

}

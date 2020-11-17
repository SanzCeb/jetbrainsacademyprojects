package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var dimension = 3;
        var ticTacToe = new Board(dimension);
        var boardPrinter = new BoardPrinter(ticTacToe);

        //1. Prints an empty field at the beginning of the game.
        boardPrinter.printBoard();

        //2. Creates a game loop where the program asks the user to enter the cell coordinates,
        // analyzes the move for correctness, and shows a field with the changes if everything is ok.
        while(ticTacToe.isNotFinished()) {
            int coordinateY;
            int coordinateX;

            System.out.print("Enter the coordinates: ");
            try {
                 coordinateY = scanner.nextInt() - 1;
                 coordinateX = dimension - scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
                continue;
            }
            if (! ticTacToe.areCoordinatesInBound(coordinateX, coordinateY)) {
                System.out.printf("Coordinates should be from 1 to %d!%n", dimension);
            } else if (ticTacToe.isCellOccupied(coordinateX, coordinateY)) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                ticTacToe.put(coordinateX, coordinateY);
                boardPrinter.printBoard();
            }

        }
        //3. Ends the game when someone wins or when there is a draw
        boardPrinter.printGameState();
    }

}

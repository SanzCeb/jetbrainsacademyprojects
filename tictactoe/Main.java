package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cells: ");
        var input = scanner.nextLine().toCharArray();
        var ticTacToe = new Board(3, input);

        ticTacToe.printBoard();
        System.out.println(ticTacToe.getGameState());
    }
}

package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter cells: ");
        var gameState = scanner.nextLine();

        System.out.println("---------");
        System.out.printf("| %c %c %c |%n", gameState.charAt(0), gameState.charAt(1), gameState.charAt(2));
        System.out.printf("| %c %c %c |%n", gameState.charAt(3), gameState.charAt(4), gameState.charAt(5));
        System.out.printf("| %c %c %c |%n", gameState.charAt(6), gameState.charAt(7), gameState.charAt(8));
        System.out.println("---------");
    }
}

package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length:");
        var secretCodeLength = Integer.parseInt(scanner.nextLine());

        if (secretCodeLength > 10) {
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
        } else {
            System.out.println("Okay, let's start a game!");
            var secretCode = secretCodeWithUniqueDigits(secretCodeLength);

            String answer;
            int[] bullsAndCows;
            int turns = 1;
            do {
                System.out.printf("Turn %d:%n", turns++);
                answer = scanner.nextLine();
                bullsAndCows = gradeGuessingAttempt(answer, secretCode);
                System.out.println(buildGameResponse(bullsAndCows[0], bullsAndCows[1]));
            } while (bullsAndCows[0] != secretCodeLength);
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }

    static StringBuilder secretCodeWithUniqueDigits(int secretCodeLength) {
        StringBuilder secretCodeBuilder = new StringBuilder();
        var randomNumbersGenerator = new Random();

        randomNumbersGenerator.ints(0, 10)
                .distinct()
                .limit(secretCodeLength)
                .forEach(secretCodeBuilder::append);

        return secretCodeBuilder;
    }

    public static int[] gradeGuessingAttempt(String userResponse, StringBuilder secretCode) {
        int bulls = 0, cows = 0;
        var secretCodeLength = secretCode.length();
        var userResponseIterator = userResponse.chars().iterator();

        for (int i = 0; i < secretCodeLength && userResponseIterator.hasNext(); i++) {
                var userResponseChar = userResponseIterator.next();
                if (secretCode.charAt(i) == userResponseChar) {
                    bulls++;
                } else if (secretCode.chars().anyMatch(digit -> digit == userResponseChar)) {
                    cows++;
                }
        }

        return new int[]{bulls, cows};
    }

    public static StringBuilder buildGameResponse(int bulls, int cows){
        StringBuilder gameResponse = new StringBuilder("Grade: ");

        if (bulls > 0) {
            gameResponse.append(String.format("%d bulls", bulls));
            if (bulls == 1) {
                gameResponse.deleteCharAt(gameResponse.length() - 1);
            }
        }

        if (bulls > 0 && cows > 0) {
            gameResponse.append(" and ");
        }

        if (cows > 0) {
            gameResponse.append(String.format("%d cows", cows));
            if (cows == 1) {
                gameResponse.deleteCharAt(gameResponse.length() - 1);
            }
        }

        if (bulls == 0 && cows == 0) {
            gameResponse.append("None.");
        }

        return gameResponse;
    }

}

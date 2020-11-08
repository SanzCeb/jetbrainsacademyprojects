package bullscows;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        long pseudoRandomNumber = System.nanoTime();
        var secretCodeLength = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        if (secretCodeLength > 10) {
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
        } else {
            var reversedNumber = sb.append(pseudoRandomNumber).reverse();
            System.out.printf("The random secret number is %s%n", secretCodeWithUniqueDigits(reversedNumber, secretCodeLength));
        }
    }

    static StringBuilder secretCodeWithUniqueDigits(StringBuilder reversedNumber, int secretCodeLength) {
        StringBuilder secretCodeBuilder = new StringBuilder();
        for (int i = 0; i < reversedNumber.length() && secretCodeBuilder.length() < secretCodeLength; i++) {
            var numberI = reversedNumber.charAt(i);
            if ( !secretCodeBuilder.chars().anyMatch(digit -> digit == numberI)) {
                secretCodeBuilder.append(numberI);
            }
        }
        return secretCodeBuilder;
    }

    public static void bullsAndCows() {
        var secretCode = new char[]{'9', '3', '0', '5'};
        var answer = new Scanner(System.in).nextLine();
        int bulls = 0, cows = 0;
        for (int i = 0; i < secretCode.length; i++) {
            if (secretCode[i] == answer.charAt(i)) {
                bulls++;
            } else if (answer.contains(String.valueOf(secretCode[i]))){
                cows++;
            }
        }

        if (bulls > 0) {
            if (cows > 0) {
                System.out.printf("Grade: %d bull(s) and %d cows(s). The secret code is 9305.", bulls, cows);
            } else {
                System.out.printf("Grade: %d bull(s). The secret code is 9305.", bulls);
            }
        } else if (cows > 0) {
            System.out.printf("Grade: %d cows(s). The secret code is 9305.", cows);
        } else {
            System.out.printf("Grade: None. The secret code is 9305.");
        }
    }
}

package bullscows;

import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    final static int MAX_LENGTH = 36;
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");
        var secretCodeLengthStr = scanner.nextLine();
        if (isNotANumber(secretCodeLengthStr)) {
            System.out.printf("Error: \"%s\" is not a number.%n",secretCodeLengthStr);
            return;
        }
        int secretCodeLength = Integer.parseInt(secretCodeLengthStr);

        System.out.println("Input the number of possible symbols in the code:");
        var secretSymbolsLengthStr = scanner.nextLine();
        if (isNotANumber(secretCodeLengthStr)) {
            System.out.printf("Error: \"%s\" is not a number.%n",secretCodeLengthStr);
            return;
        }
        int secretSymbolsLength = Integer.parseInt(secretSymbolsLengthStr);


        if ( secretCodeLength > MAX_LENGTH || secretCodeLength <= 0) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.%n", secretCodeLength);
        } else if (secretSymbolsLength < 1 ) {
            System.out.println("Error: minimum number of possible symbols in the code is 1 (0-0).");
        } else if (secretSymbolsLength > MAX_LENGTH){
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else if (secretCodeLength > secretSymbolsLength) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n", secretCodeLength, secretSymbolsLength);
        } else {
            var possibleSymbols = generatePossibleSymbols(secretSymbolsLength);
            var secretCode = secretCodeWithUniqueDigitsAndSymbols(possibleSymbols, secretCodeLength);
            var asterisks = generateAsterisks(secretCodeLength);
            var symbolsRange = generateSymbolsRangeMessage(possibleSymbols);
            System.out.printf("The secret is prepared: %s %s.%n", asterisks, symbolsRange);
            System.out.println("Okay, let's start a game!");
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

    private static boolean isNotANumber(String secretCodeLengthStr) {
        return secretCodeLengthStr.chars().filter(i -> '0' <= i && i <= '9').count() != secretCodeLengthStr.length();
    }

    private static String generateAsterisks(int secretCodeLength) {
        return "*".repeat(secretCodeLength);
    }

    private static String generateSymbolsRangeMessage(String possibleSymbols) {
        var possibleSymbolsLength = possibleSymbols.length();
        var closeRange = Math.min(9, possibleSymbolsLength - 1);
        var numbersRange = String.format("(0-%d", closeRange);
        var symbolsRangeMessageBuilder = new StringBuilder(numbersRange);

        if (possibleSymbolsLength > 10) {
            symbolsRangeMessageBuilder.append(", a");
            var lastChar = possibleSymbols.charAt(possibleSymbolsLength - 1);
            if (lastChar > 'a') {
                symbolsRangeMessageBuilder.append(String.format("-%c", lastChar));
            }
        }
        symbolsRangeMessageBuilder.append(')');
        return  symbolsRangeMessageBuilder.toString();
    }

    private static String generatePossibleSymbols(int secretSymbolsLength) {
        var numDigits = Math.min(10, secretSymbolsLength);
        var numSymbols = Math.max(0, secretSymbolsLength - numDigits);
        var possibleDigits = IntStream.range(0,numDigits).map(i -> '0' + i);
        var possibleLetters = IntStream.range(0,numSymbols).map(i -> 'a' + i);
        return IntStream.concat(possibleDigits, possibleLetters)
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }

    static String secretCodeWithUniqueDigitsAndSymbols(String possibleSymbols, int secretCodeLength) {
        return new Random()
                .ints(0, possibleSymbols.length())
                .distinct()
                .limit(secretCodeLength)
                .map(possibleSymbols::charAt)
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }

    public static int[] gradeGuessingAttempt(String userResponse, String secretCode) {
        int bulls = 0, cows = 0;
        var secretCodeLength = secretCode.length();
        var userResponseIterator = userResponse.codePoints().iterator();

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

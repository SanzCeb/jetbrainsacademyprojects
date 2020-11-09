package correcter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var userInput = scanner.nextLine();
        var userInputEncoded = encode(userInput);
        var userInputEncodedWithErrors = splitUserInput(3, userInputEncoded)
                .stream().map(Main::simulateError)
                .collect(Collectors.joining());
        var userInputDecoded = splitUserInput(3, userInputEncodedWithErrors)
                .stream().map(Main::decode)
                .collect(Collectors.joining());

        System.out.println(userInput);
        System.out.println(userInputEncoded);
        System.out.println(userInputEncodedWithErrors);
        System.out.println(userInputDecoded);

    }

    private static String decode(String s) {
        return Character.toString((s.charAt(0) == s.charAt(1)) || (s.charAt(0) == s.charAt(2))
                ? s.charAt(0) : s.charAt(1));
    }

    private static String encode(String userInput) {
        var simbolsTripled = new StringBuilder();
        for (int i = 0; i < userInput.length(); i++) {
            var symbolTripled = Character.toString(userInput.charAt(i)).repeat(3);
            simbolsTripled.append(symbolTripled);
        }
        return simbolsTripled.toString();
    }

    private static Collection<String> splitUserInput(int tokenLength, String userInput) {
        var splitUserInput = new ArrayList<String>();
        var remainingUserInput = userInput;

        while (!remainingUserInput.isEmpty()) {
            var endIndex = Math.min(tokenLength, remainingUserInput.length());
            splitUserInput.add(remainingUserInput.substring(0, endIndex));
            remainingUserInput = remainingUserInput.substring(endIndex);
        }

        return splitUserInput;
    }

    private static String simulateError (String str) {
        var stringWithError = str;

        if (str.length() == 3) {

            var randomPosition = new Random().nextInt(str.length());
            var randomCharacter = Character.toString(CharRandomizer.getRandomChar());
            var replacedCharacter = Character.toString(str.charAt(randomPosition));

            while (randomCharacter.equals(replacedCharacter)) {
                randomCharacter = Character.toString(CharRandomizer.getRandomChar());
            }

            stringWithError = str.replaceFirst(replacedCharacter, randomCharacter);
        }

        return stringWithError;
    }


}

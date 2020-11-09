package correcter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var userInput = scanner.nextLine();
        var tokens = splitUserInput(3, userInput);
        var tokensWithError = tokens.stream()
                .map(Main::simulateError);

        tokensWithError.forEach(System.out::println);
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
            var randomCharacter = Character.toString(getRandomCharacter());
            var replacedCharacter = Character.toString(str.charAt(randomPosition));

            while (randomCharacter.equals(replacedCharacter)) {
                randomCharacter = Character.toString(getRandomCharacter());
            }

            stringWithError = str.replaceFirst(replacedCharacter, randomCharacter);
        }

        return stringWithError;
    }

    private static char getRandomCharacter() {
        var allCharactersBuilder = IntStream.builder();
        var lowerCaseLetters = IntStream.rangeClosed('a', 'z');
        var upperCaseLetters = IntStream.rangeClosed('A', 'Z');
        var numbers = IntStream.rangeClosed('0', '9');

        allCharactersBuilder.add(' ');
        numbers.forEach(allCharactersBuilder::add);
        lowerCaseLetters.forEach(allCharactersBuilder::add);
        upperCaseLetters.forEach(allCharactersBuilder::add);
        var allCharacters = allCharactersBuilder.build();
        var randomPosition = new Random().nextInt(63);
        var iterator = allCharacters.iterator();

        while (randomPosition > 0){
            iterator.nextInt();
            randomPosition--;
        }

        return (char)iterator.nextInt();
    }


}

package correcter;

import java.util.Random;
import java.util.stream.IntStream;

public class CharRandomizer {

    private static final int[] CHARACTERS;
    private static final Random randomizer = new Random();

    static {
        var allCharactersBuilder = IntStream.builder();
        var lowerCaseLetters = IntStream.rangeClosed('a', 'z');
        var upperCaseLetters = IntStream.rangeClosed('A', 'Z');
        var numbers = IntStream.rangeClosed('0', '9');

        allCharactersBuilder.add(' ');
        numbers.forEach(allCharactersBuilder::add);
        lowerCaseLetters.forEach(allCharactersBuilder::add);
        upperCaseLetters.forEach(allCharactersBuilder::add);
        CHARACTERS = allCharactersBuilder.build().toArray();
    }

    static char getRandomChar() {
        var randomPosition = randomizer.nextInt(CHARACTERS.length);
        return (char) CHARACTERS[randomPosition];
    }

}

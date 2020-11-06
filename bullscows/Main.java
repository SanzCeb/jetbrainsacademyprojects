package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var secretCode = new char[]{'9', '3', '0', '5'};
        var answer = scanner.nextLine();
        var bulls = 0;
        var cows = 0;

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

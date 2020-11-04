package flashcards;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    final static String FILE_INPUT_MSG = "File name:\n";
    final static Scanner SCANNER = new Scanner(System.in);
    final static Flashcards FLASHCARDS = new Flashcards();
    final static List<String> consoleLog = new ArrayList<>();
    static boolean exportAtExit;
    static String exportFileName;

    public static void main(String[] args) throws IOException {
        runCommandLineArguments(args);
        actionMenu();
    }

    private static void runCommandLineArguments(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-import")) {
                importFlashcards(args[i + 1]);
            } else if (args[i].equals("-export")){
                exportAtExit = true;
                exportFileName = args[i + 1];
            }
        }
    }

    private static void actionMenu() throws IOException {
        boolean exit = false;
        do {
            printMessage("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\n");
            var action = readSystemInput();
            if (action.equalsIgnoreCase("add")) {
                addFlashcard();
            } else if (action.equalsIgnoreCase("remove")) {
                removeFlashcard();
            } else if (action.equalsIgnoreCase("import")) {
                importFlashcards();
            } else if (action.equalsIgnoreCase("export")) {
                exportFlashcard();
            } else if (action.equalsIgnoreCase("ask")) {
                askQuestions();
            } else if (exit = action.equalsIgnoreCase("exit")) {
                actionExit();
            } else if (action.equalsIgnoreCase("log")) {
                actionLog();
            } else if (action.equalsIgnoreCase("hardest card")) {
                printHardestCard();
            } else if (action.equalsIgnoreCase("reset stats")) {
                resetStats();
            }
        } while (!exit);
    }

    private static String readSystemInput() {
        var readLine = SCANNER.nextLine();
        consoleLog.add(readLine + '\n');
        return readLine;
    }

    private static void printMessage(String message) {
        System.out.print(message);
        consoleLog.add(message);
    }

    private static void resetStats() {
        FLASHCARDS.clearMistakes();
        printMessage("Card statistics have been reset.\n");
    }

    private static void printHardestCard() {
        var hardestCards = FLASHCARDS.hardestCards();
        if (hardestCards.isEmpty()) {
            printMessage("There are no cards with errors\n");
        } else {
            String header = hardestCards.size() > 1 ? "The hardest cards are" : "The hardest card is";
            var hardestCardFormatted = hardestCards.stream()
                    .map(term -> String.format("\"%s\"", term))
                    .collect(Collectors.joining(", "));
            var mistakes = FLASHCARDS.getMistakes(hardestCards.get(0));
            var message = String.format("%s %s. You have %d errors answering them.%n", header, hardestCardFormatted, mistakes);
            printMessage(message);
        }

    }

    private static void actionLog() throws IOException {
        printMessage(FILE_INPUT_MSG);
        File file = new File(readSystemInput());
        try (var writer = new PrintWriter(file)) {
            consoleLog.forEach(stringLog -> writer.write(stringLog));
        }
        printMessage("The log has been saved.\n");
    }

    private static void actionExit() throws IOException {
        printMessage("Bye bye!\n");
        if (exportAtExit) {
            exportFlashcard(exportFileName);
        }
    }

    private static void askQuestions() {
        printMessage("How many times to ask?\n");
        var numberOfTimes = Integer.parseInt(readSystemInput());
        while (numberOfTimes > 0) {
            askAQuestion();
            numberOfTimes--;
        }
    }

    private static void askAQuestion() {
        String card = FLASHCARDS.getRandomCard();
        var message = String.format("Print the definition of \"%s\":\n", card);
        printMessage(message);
        String answer = readSystemInput();
        String rightAnswer = FLASHCARDS.getDefinition(card);
        if (answer.equalsIgnoreCase(rightAnswer)) {
            printMessage("Correct!\n");
        } else {
            FLASHCARDS.addMistake(card);
            var answeredCard = FLASHCARDS.getCard(answer);
            if (answeredCard.isPresent()) {
                printMessage(String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".%n", rightAnswer, answeredCard.get()));
            } else {
                printMessage(String.format("Wrong. The right answer is \"%s\"%n", rightAnswer));
            }
        }
    }

    private static void addFlashcard() {
        printMessage("The card:\n");
        String card = readSystemInput();
        printMessage("The definition of the card:\n");
        String definition = readSystemInput();
        FLASHCARDS.add(card, definition);
        printMessage(String.format("The pair (\"%s\":\"%s\") has been added.%n", card, definition));
    }

    private static void removeFlashcard() {
        printMessage("Which card?\n");
        String card = readSystemInput();
        if (FLASHCARDS.remove(card)) {
            printMessage("The card has been removed.\n");
        } else {
            var message = String.format("Can't remove \"%s\": there is no such a card.%n", card);
            printMessage(message);
        }
    }

    private static void importFlashcards(String filename) {
        File file = new File(filename);
        int importedFlashcards = 0;
        try (var scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String card = scanner.nextLine();
                String definition = scanner.nextLine();
                int mistakes = Integer.parseInt(scanner.nextLine());
                FLASHCARDS.importFlashcard(card, definition, mistakes);
                importedFlashcards++;
            }
            var message = String.format("%d cards have been loaded.%n", importedFlashcards);
            printMessage(message);
        } catch (FileNotFoundException exception) {
            printMessage("File not found.\n");
        }
    }

    private static void importFlashcards() {
        printMessage(FILE_INPUT_MSG);
        String filename = readSystemInput();
        importFlashcards(filename);
    }

    private static void exportFlashcard(String filename) throws IOException{
        File file = new File(filename);
        try (var writer = new PrintWriter(file)){
            var exportedFlashcards = FLASHCARDS.exportFlashcards(writer);
            var message = String.format("%d cards have been saved.%n", exportedFlashcards);
            printMessage(message);
        }
    }

    private static void exportFlashcard() throws IOException {
        printMessage(FILE_INPUT_MSG);
        String fileName = readSystemInput();
        exportFlashcard(fileName);
    }
}



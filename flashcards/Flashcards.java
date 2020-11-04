package flashcards;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

class Flashcards {
    private List<Flashcard> flashcards;

    Flashcards() {
        this.flashcards = new ArrayList();
    }

    void add(String card, String definition) {
        flashcards.add(new Flashcard(card, definition));
    }

    boolean remove(String card) {
        return flashcards.removeIf(flashcard -> flashcard.getTerm().equals(card));
    }

    String getRandomCard() {
        var randomIndex = new Random().nextInt(flashcards.size());
        return flashcards.get(randomIndex).getTerm();
    }

    String getDefinition(String card) {
        return getFlashcardByCard(card).getDefinition();
    }

    void addMistake(String card) {
        getFlashcardByCard(card).addMistake();
    }

    Optional<String> getCard(String definition) {
        return flashcards.stream()
                .filter(flashcard -> flashcard.getDefinition().equals(definition))
                .findFirst()
                .map(Flashcard::getTerm);
    }

    private Flashcard getFlashcardByCard(String card) {
        return flashcards.stream().filter(flashcard -> flashcard.getTerm().equals(card))
                .findFirst().get();
    }

    List<String> hardestCards() {
        flashcards.sort(Comparator.comparingInt(Flashcard::getMistake).reversed());
        return flashcards.stream().findFirst()
                .filter(flashcard -> flashcard.mistake > 0)
                .map(flashcard -> flashcards.stream().filter(flashcard1 -> flashcard.mistake == flashcard1.mistake))
                .map(flashcardStream -> flashcardStream.map(Flashcard::getTerm))
                .map(stringStream -> stringStream.collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }

    public int getMistakes(String card) {
        return getFlashcardByCard(card).mistake;
    }

    void clearMistakes() {
        flashcards.forEach(flashcard -> flashcard.mistake = 0);
    }

    void importFlashcard(String card, String definition, int mistakes) {
        try {
            var flashcard = getFlashcardByCard(card);
            flashcard.mistake = mistakes;
            flashcard.definition = definition;
        } catch (NoSuchElementException exception) {
            flashcards.add(new Flashcard(card, definition, mistakes));
        }
    }

    int exportFlashcards(PrintWriter writer) {
        for (var flashcard : flashcards) {
            writer.println(flashcard.getTerm());
            writer.println(flashcard.getDefinition());
            writer.println(flashcard.getMistake());
        }
        return flashcards.size();
    }

    class Flashcard {
        private String term;
        private String definition;
        private int mistake;

        Flashcard(String term, String definition, int mistake) {
            this.term = term;
            this.definition = definition;
            this.mistake = mistake;
        }

        Flashcard(String term, String definition) {
            this(term, definition, 0);
        }

        String getTerm() {
            return term;
        }

        String getDefinition() {
            return definition;
        }

        int getMistake() {
            return mistake;
        }

        void addMistake() {
            mistake++;
        }
    }
    
}
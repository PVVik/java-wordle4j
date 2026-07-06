package ru.yandex.practicum;

import ru.yandex.practicum.exception.WordLengthException;
import ru.yandex.practicum.exception.WordNotFoundInDictionary;

import java.util.ArrayList;
import java.util.List;

public class WordleGame {

    private String answer;
    private int steps = 0;
    private final WordleDictionary dictionary;
    private final List<String> guesses = new ArrayList<>();
    private final List<StringBuilder> hints = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isValid(String word) throws WordLengthException, WordNotFoundInDictionary {
        if (word.length() != 5) {
            System.out.println("Загадайте слово из 5 букв!");
            throw new WordLengthException();

        }

        if (!dictionary.getWords().contains(word)) {
            System.out.println("Я пока не знаю такого слова. Загадайте другое, пожалуйста!");
            throw new WordNotFoundInDictionary();
        }

        return true;
    }

    public StringBuilder getHint(String word, String guess) {
        StringBuilder hint = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess.charAt(i)) {
                hint.append("+");
            } else if (word.contains(String.valueOf(guess.charAt(i)))) {
                hint.append("^");
            } else hint.append("-");
        }

        return hint;
    }

    public List<String> getSuitWords(List<String> listWords, String guess, StringBuilder hint) {
        List<String> finalList = new ArrayList<>();

        for (String word : listWords) {
            if (getHint(word, guess).toString().equals(hint.toString())) {
                finalList.add(word);
            }
        }

        return finalList;
    }

    public void addStep() {
        steps += 1;
    }

    public boolean isStepTooMuch() {
        return steps >= 6;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void addGuess(String guess) {
        guesses.add(guess);
    }

    public void addHint(StringBuilder hint) {
        hints.add(hint);
    }

    @Override
    public String toString() {
        return "WordleGame{" +
                "answer='" + answer + '\'' +
                ", steps=" + steps +
                ", dictionary=" + dictionary +
                ", guesses=" + guesses +
                ", hints=" + hints +
                '}';
    }
}

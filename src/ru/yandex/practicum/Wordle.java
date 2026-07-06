package ru.yandex.practicum;

import ru.yandex.practicum.exception.WordLengthException;
import ru.yandex.practicum.exception.WordNotFoundInDictionary;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) throws IOException {

        final Random random = new Random();
        final Scanner scanner = new Scanner(System.in);
        boolean isWordNotGuess = true;

        Files.deleteIfExists(Path.of("logFile.txt"));
        Path path = Files.createFile(Path.of("logFile.txt"));
        Writer fileWriter = new FileWriter(path.toFile());

        try {
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader("words_ru.txt");
            WordleDictionary wordleDictionary = new WordleDictionary(wordleDictionaryLoader);

            List<String> words = wordleDictionary.getWords();
            String computerWord = words.get(random.nextInt(words.size()));
            WordleGame wordleGame = new WordleGame(wordleDictionary);

            System.out.println("Я загадал слово. Попробуйте его угадать.");
            System.out.println("Введите слово из 5 букв или попросите подсказку, нажав Enter.");

            while (isWordNotGuess) {
                try {
                    String guess = scanner.nextLine();

                    if (guess.equals("")) {
                        guess = words.get(random.nextInt(words.size()));
                        System.out.println(guess);
                    }

                    wordleGame.addGuess(guess);

                    if (!wordleGame.isValid(guess)) {
                        continue;
                    }

                    wordleGame.setAnswer(guess);
                    wordleGame.addStep();

                    if (wordleGame.isStepTooMuch()) {
                        System.out.println("Проигрыш, увы!");
                        break;
                    }

                    if (computerWord.equals(guess)) {
                        System.out.println("Выигрыш! Ура!");
                        isWordNotGuess = false;
                    }

                    StringBuilder hint = wordleGame.getHint(computerWord, guess);
                    wordleGame.addHint(hint);
                    words = wordleGame.getSuitWords(words, guess, hint);

                    fileWriter.write(wordleGame.toString());
                    fileWriter.write("\n");

                    System.out.println(hint);

                } catch (WordNotFoundInDictionary | WordLengthException e) {
                    fileWriter.write(Arrays.toString(e.getStackTrace()));
                    fileWriter.write("\n");
                }
            }
        } catch (IOException e) {
            fileWriter.write(Arrays.toString(e.getStackTrace()));
            fileWriter.write("\n");
            fileWriter.flush();
        }
    }
}



package ru.yandex.practicum;

import ru.yandex.practicum.exception.WordleDictionaryException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    public static final int WORD_LENGTH = 5;
    private final BufferedReader br;

    public WordleDictionaryLoader(String fileName) throws WordleDictionaryException {
        try {
            br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Что-то не так со словарём. Проверьте файл, пожалуйста!");
            throw new WordleDictionaryException();
        }
    }

    public List<String> readFile() throws IOException {
        List<String> words = new ArrayList<>();

        while (br.ready()) {
            String word = br.readLine();
            if (word.length() == WORD_LENGTH) {
                word.toLowerCase();

                if (word.contains("ё")) {
                    word.replace("ё", "е");
                }

                words.add(word);
            }
        }

        br.close();

        return words;
    }
}

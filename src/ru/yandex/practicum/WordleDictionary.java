package ru.yandex.practicum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionary {

    private final List<String> words;

    public WordleDictionary(WordleDictionaryLoader wordleDictionaryLoader) throws IOException {
        words = wordleDictionaryLoader.readFile();
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }

}

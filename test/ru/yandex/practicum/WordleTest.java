package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.WordLengthException;
import ru.yandex.practicum.exception.WordNotFoundInDictionary;
import ru.yandex.practicum.exception.WordleDictionaryException;

import java.io.IOException;

class WordleTest {

    WordleDictionary dictionary;
    WordleGame game;

    @BeforeEach
    public void setUp() throws IOException {
        dictionary = new WordleDictionary(new WordleDictionaryLoader("words_ru.txt"));
        game = new WordleGame(dictionary);
    }

    @Test
    public void wordleDictionaryLoaderTest_shouldGetExceptionWithNonExistentFile() {
        WordleDictionaryException e = Assertions.assertThrows(WordleDictionaryException.class, () ->
                new WordleDictionaryLoader("someFile.txt"));
    }

    @Test
    public void wordleGameTest_shouldGetExceptionIfWordWrongLength() {
        Assertions.assertThrows(WordLengthException.class, () -> game.isValid("путь"));
        Assertions.assertThrows(WordLengthException.class, () -> game.isValid("библиотека"));
    }

    @Test
    public void wordleGameTest_shouldGetExceptionIfWordNotFromDictionary() {
        Assertions.assertThrows(WordNotFoundInDictionary.class, () -> game.isValid("exist"));
    }

    @Test
    public void wordleGameTest_shouldGetHint() {
        Assertions.assertEquals(new StringBuilder("-++++").toString(), game.getHint("валец", "палец").toString());
    }

}

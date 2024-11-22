package de.andidebob.textbased.vigenere;

import de.andidebob.textbased.alphabet.AlphabetKey;

import java.util.HashMap;

public class VigenereTable {

    private final HashMap<Character, AlphabetKey> keys = new HashMap<>();

    private VigenereTable() {
        for (int i = 0; i < 26; i++) {
            keys.put((char) ('a' + i), AlphabetKey.withCaesarShift(i));
            keys.put((char) ('A' + i), AlphabetKey.withCaesarShift(i));
        }
    }

    public static final VigenereTable instance = new VigenereTable();

    public char map(char key, char character) {
        return keys.get(key).map(character);
    }
}

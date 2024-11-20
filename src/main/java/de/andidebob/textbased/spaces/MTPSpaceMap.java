package de.andidebob.textbased.spaces;

import java.util.HashMap;

public class MTPSpaceMap {

    public static final MTPSpaceMap instance = new MTPSpaceMap();
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String PUNCTUATION = "():!?,-.";
    private static final String NUMBERS = "0123456789";

    private final HashMap<Character, Character> allMappings = new HashMap<>();
    private final HashMap<Character, Character> letters = new HashMap<>();
    private final HashMap<Character, Character> punctuation = new HashMap<>();
    private final HashMap<Character, Character> numbers = new HashMap<>();

    private MTPSpaceMap() {
        letters.put(getXOR(' '), ' ');
        for (char c : LETTERS.toCharArray()) {
            letters.put(getXOR(c), c);
        }
        allMappings.putAll(letters);
        punctuation.put(getXOR(' '), ' ');
        for (char c : PUNCTUATION.toCharArray()) {
            punctuation.put(getXOR(c), c);
        }
        allMappings.putAll(punctuation);
        numbers.put(getXOR(' '), ' ');
        for (char c : NUMBERS.toCharArray()) {
            numbers.put(getXOR(c), c);
        }
        allMappings.putAll(numbers);
    }

    public boolean isAlphabeticXOR(char c) {
        return letters.containsKey(c);
    }

    public boolean isPunctuationXOR(char c) {
        return punctuation.containsKey(c);
    }

    public boolean isNumberXOR(char c) {
        return numbers.containsKey(c);
    }

    public boolean isSpaceXOR(char c) {
        return allMappings.containsKey(c);
    }

    public char getMappingFor(char c) {
        return allMappings.get(c);
    }

    private char getXOR(char c) {
        return (char) (c ^ ' ');
    }
}

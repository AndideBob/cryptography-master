package de.andidebob.monoalphabetic;


import de.andidebob.frequency.CharacterFrequencyResult;

import java.util.HashMap;

public class AlphabetMatcher {

    private final HashMap<Character, Character> alphabet;

    public AlphabetMatcher(CommonCharacterDefinition characterDefinition, CharacterFrequencyResult characterFrequencyResult) {
        alphabet = new HashMap<>();
        Character[] charactersInFrequencyOrder = characterFrequencyResult.getFrequencies().stream().map(CharacterFrequencyResult.CharacterFrequency::character).toArray(Character[]::new);
        for (int i = 0; i < charactersInFrequencyOrder.length; i++) {
            alphabet.put(charactersInFrequencyOrder[i], characterDefinition.getByRarityIndex(i));
        }
    }

    public char map(char c) {
        if (!alphabet.containsKey(c)) {
            return c;
        }
        return alphabet.get(c);
    }

    public String mapAll(String input) {
        StringBuilder result = new StringBuilder();
        char[] characters = input.toCharArray();
        for (char character : characters) {
            result.append(map(character));
        }
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AlphabetMatcher{:\n");
        alphabet.forEach((original, mapped) -> sb.append(String.format("%s -> %s\n", original, mapped)));
        return sb.toString();
    }
}

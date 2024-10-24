package de.andidebob.monoalphabetic;


import de.andidebob.frequency.CharacterFrequency;
import de.andidebob.frequency.CharacterFrequencyResult;
import de.andidebob.monoalphabetic.language.LanguageModel;

import java.util.HashMap;
import java.util.Objects;

public class AlphabetKey {

    private final HashMap<Character, Character> alphabet;

    private AlphabetKey() {
        alphabet = new HashMap<>();
    }

    public AlphabetKey(LanguageModel languageModel) {
        alphabet = new HashMap<>();
        languageModel.getFrequencies().forEach(
                characterFrequency -> alphabet.put(characterFrequency.character(), characterFrequency.character()));
    }

    public AlphabetKey(LanguageModel languageModel, CharacterFrequencyResult characterFrequencyResult) {
        alphabet = new HashMap<>();
        Character[] charactersInFrequencyOrder = characterFrequencyResult.getFrequencies().stream().map(CharacterFrequency::character).toArray(Character[]::new);
        for (int i = 0; i < charactersInFrequencyOrder.length; i++) {
            alphabet.put(charactersInFrequencyOrder[i], languageModel.getByRarityIndex(i).character());
        }
    }

    public void overrideMapping(char from, char to) {
        alphabet.put(from, to);
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
        StringBuilder sb = new StringBuilder("AlphabetMatcher:\n");
        alphabet.forEach((original, mapped) -> sb.append(String.format("%s -> %s\n", original, mapped)));
        return sb.toString();
    }

    public AlphabetKey copy() {
        AlphabetKey newKey = new AlphabetKey();
        newKey.alphabet.putAll(alphabet);
        return newKey;
    }

    @Override
    public int hashCode() {
        return alphabet.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlphabetKey that = (AlphabetKey) o;
        return Objects.equals(alphabet, that.alphabet);
    }
}

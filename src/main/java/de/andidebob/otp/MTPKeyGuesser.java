package de.andidebob.otp;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MTPKeyGuesser {

    private final int maxLength;
    private final Set<HexString> cipherTexts;

    public MTPKeyGuesser(Collection<HexString> cipherTexts) {
        this.maxLength = cipherTexts.stream()
                .max(Comparator.comparingInt(HexString::getLength))
                .orElseThrow(() -> new RuntimeException("Expected at least one element!"))
                .getLength();
        // Pad Strings to same length
        this.cipherTexts = cipherTexts.stream()
                .map(c -> c.padToLength(maxLength))
                .collect(Collectors.toSet());
    }


    public ArrayList<Set<Character>> determinePossibleKeyCharacters() {
        ArrayList<Set<Character>> keyCharacterGuesses = new ArrayList<>();
        // Initialize with '0' (null byte for XOR)
        for (int i = 0; i < maxLength / 2; i++) {
            keyCharacterGuesses.add(new HashSet<>());
        }

        HashSet<HexString> coveredXORs = new HashSet<>();
        for (HexString ciphertextA : cipherTexts) {
            for (HexString ciphertextB : cipherTexts) {
                if (ciphertextA.equals(ciphertextB)) {
                    continue;
                }
                HexString xor = ciphertextA.xor(ciphertextB);
                if (!coveredXORs.add(xor)) {
                    continue;
                }
                int guessIndex = 0;
                for (int i = 0; i < xor.getLength() - 1; i += 2) {
                    HexString current = xor.getHexCharAt(i);
                    // Ignore if xor is the same as one of the ciphertexts (because it was XORed with 00)
                    if (current.equals(ciphertextA.getHexCharAt(i)) || current.equals(ciphertextB.getHexCharAt(i))) {
                        continue;
                    }
                    Set<StringXORMap.CharacterMapping> possibleMappings = StringXORMap.getInstance().getPossibleMappings(current);
                    int plainTextCharPos = i / 2;
                    char currentCharA = ciphertextA.convertToString().charAt(plainTextCharPos);
                    char currentCharB = ciphertextB.convertToString().charAt(plainTextCharPos);
                    Set<Character> possibleKeyCharacters = possibleMappings.stream()
                            .flatMap(cm -> extractMatchingCharacters(cm, currentCharA, currentCharB))
                            .collect(Collectors.toSet());
                    keyCharacterGuesses.get(guessIndex++).addAll(possibleKeyCharacters);
                }
            }
        }
        return keyCharacterGuesses;
    }

    private Stream<Character> extractMatchingCharacters(StringXORMap.CharacterMapping mapping, char a, char b) {
        HashSet<Character> characters = new HashSet<>();
        if (mapping.a() == a) {
            characters.add(mapping.b());
        }
        if (mapping.b() == a) {
            characters.add(mapping.a());
        }
        if (mapping.a() == b) {
            characters.add(mapping.b());
        }
        if (mapping.b() == b) {
            characters.add(mapping.a());
        }
        return characters.stream();
    }
}

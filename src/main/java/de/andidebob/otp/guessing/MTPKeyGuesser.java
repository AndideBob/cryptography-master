package de.andidebob.otp.guessing;

import de.andidebob.otp.HexString;
import de.andidebob.otp.StringXORMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MTPKeyGuesser {

    private final int maxLength;
    private final HashSet<HexString> cipherTexts;

    public MTPKeyGuesser(List<HexString> paddedXORs) {
        this.maxLength = paddedXORs.getFirst().getLength();
        this.cipherTexts = new HashSet<>(paddedXORs);
    }


    public ArrayList<KeyCharacterProbability> determinePossibleKeyCharacters() {
        ArrayList<KeyCharacterProbability> keyCharacterGuesses = new ArrayList<>();
        // Initialize with '0' (null byte for XOR)
        for (int i = 0; i < maxLength / 2; i++) {
            keyCharacterGuesses.add(new KeyCharacterProbability());
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

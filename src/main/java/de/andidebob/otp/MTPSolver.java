package de.andidebob.otp;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MTPSolver {

    public HexString determineKeyAlt(Collection<HexString> ciphertexts) {
        int maxLength = ciphertexts.stream()
                .max(Comparator.comparingInt(HexString::getLength))
                .orElseThrow(() -> new RuntimeException("Expected at least one element!"))
                .getLength();
        ArrayList<Set<Character>> keyGuess = new ArrayList<>();
        // Initialize with '0' (null byte for XOR)
        for (int i = 0; i < maxLength / 2; i++) {
            keyGuess.add(new HashSet<>());
        }

        Set<HexString> paddedCiphertexts = ciphertexts.stream()
                .map(c -> c.padToLength(maxLength))
                .collect(Collectors.toSet());
        HashSet<HexString> coveredXORs = new HashSet<>();
        for (HexString ciphertextA : paddedCiphertexts) {
            for (HexString ciphertextB : paddedCiphertexts) {
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
                    keyGuess.get(guessIndex++).addAll(possibleKeyCharacters);
                }
            }
        }
        for (int i = 0; i < keyGuess.size(); i++) {
            System.out.println(i + " -> " + keyGuess.get(i));
        }
        return new HexString("asdf");
    }

    private static Stream<Character> extractMatchingCharacters(StringXORMap.CharacterMapping mapping, char a, char b) {
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

    public HexString determineKey(Collection<HexString> ciphertexts) {
        int maxLength = ciphertexts.stream()
                .max(Comparator.comparingInt(HexString::getLength))
                .orElseThrow(() -> new RuntimeException("Expected at least one element!"))
                .getLength();
        char[] keyGuess = new char[maxLength];
        // Initialize with '0' (null byte for XOR)
        Arrays.fill(keyGuess, '0');

        Set<HexString> paddedCiphertexts = ciphertexts.stream()
                .map(c -> c.padToLength(maxLength))
                .collect(Collectors.toSet());
        System.out.println("Padded Ciphertexts:");
        paddedCiphertexts.forEach(
                System.out::println
        );

        HashSet<HexString> coveredXORs = new HashSet<>();
        for (HexString ciphertextA : paddedCiphertexts) {
            for (HexString ciphertextB : paddedCiphertexts) {
                if (ciphertextA.equals(ciphertextB)) {
                    continue;
                }
                HexString xor = ciphertextA.xor(ciphertextB);
                if (!coveredXORs.add(xor)) {
                    continue;
                }

                String xorPlaintext = xor.convertToString();
                for (int i = 0; i < xorPlaintext.length(); i++) {
                    char c = xorPlaintext.charAt(i);
                    if (isLikelySpaceOrLetter(c)) {
                        String hexRepresentation = Integer.toHexString(c);
                        keyGuess[i * 2] = hexRepresentation.charAt(0);
                        keyGuess[i * 2 + 1] = hexRepresentation.charAt(1);
                    }
                }
            }
        }
        HexString guessedKey = new HexString(new String(keyGuess));
        System.out.println("Guessed Key:");
        System.out.println(guessedKey.convertToString());
        return guessedKey;
    }

    private static boolean isLikelySpaceOrLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ';
    }
}

package de.andidebob.otp;

import java.util.*;
import java.util.stream.Collectors;

public class MTPSolver {

    public HexString determineKeyAlt(Collection<HexString> ciphertexts) {

        MTPKeyGuesser keyGuesser = new MTPKeyGuesser(ciphertexts);

        ArrayList<KeyCharacterProbability> possibleKeyCharacters = keyGuesser.determinePossibleKeyCharacters();

        for (int i = 0; i < possibleKeyCharacters.size(); i++) {
            System.out.println(i + " -> " + possibleKeyCharacters.get(i).getCharactersByProbability());
        }
        return new HexString("asdf");
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

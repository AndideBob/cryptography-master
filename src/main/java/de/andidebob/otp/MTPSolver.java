package de.andidebob.otp;

import de.andidebob.otp.guessing.KeyCharacterProbability;
import de.andidebob.otp.guessing.MTPKeyGuesser;

import java.util.*;
import java.util.stream.Collectors;

public class MTPSolver {

    public List<HexString> getPaddedXORs(Collection<HexString> ciphertexts) {
        int maxLength = ciphertexts.stream()
                .max(Comparator.comparingInt(HexString::getLength))
                .orElseThrow(() -> new RuntimeException("Expected at least one element!"))
                .getLength();

        Set<HexString> paddedCiphertexts = ciphertexts.stream()
                .map(c -> c.padToLength(maxLength))
                .collect(Collectors.toSet());
        System.out.println("Padded Ciphertexts:");
        paddedCiphertexts.forEach(
                System.out::println
        );

        ArrayList<HexString> coveredXORs = new ArrayList<>();
        for (HexString ciphertextA : paddedCiphertexts) {
            for (HexString ciphertextB : paddedCiphertexts) {
                if (ciphertextA.equals(ciphertextB)) {
                    continue;
                }
                HexString xor = ciphertextA.xor(ciphertextB);
                if (!coveredXORs.contains(xor)) {
                    coveredXORs.add(xor);
                }
            }
        }
        return coveredXORs;
    }

    public HexString determineKeyByGuessingProbability(Collection<HexString> ciphertexts) {
        List<HexString> paddedXORs = getPaddedXORs(ciphertexts);

        MTPKeyGuesser keyGuesser = new MTPKeyGuesser(paddedXORs);

        ArrayList<KeyCharacterProbability> possibleKeyCharacters = keyGuesser.determinePossibleKeyCharacters();

        for (int i = 0; i < possibleKeyCharacters.size(); i++) {
            System.out.println(i + " -> " + possibleKeyCharacters.get(i).getCharactersByProbability());
        }
        return new HexString("asdf");
    }


    public HexString determineKeyByDoingSomething(Collection<HexString> ciphertexts) {
        List<HexString> paddedXORs = getPaddedXORs(ciphertexts);

        int maxLength = paddedXORs.getFirst().getLength();
        char[] keyGuess = new char[maxLength];
        // Initialize with '0' (null byte for XOR)
        Arrays.fill(keyGuess, '0');

        for (HexString xor : paddedXORs) {
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
        HexString guessedKey = new HexString(new String(keyGuess));
        System.out.println("Guessed Key:");
        System.out.println(guessedKey.convertToString());
        return guessedKey;
    }

    private static boolean isLikelySpaceOrLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ';
    }
}

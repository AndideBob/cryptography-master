package de.andidebob.mtp;

import de.andidebob.otp.hexstring.BasicHexString;
import de.andidebob.otp.hexstring.HexString;
import de.andidebob.otp.hexstring.XORHexString;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MTPSolver {

    public abstract String[] solve(Collection<HexString> ciphertexts, String cipherTextToDecipher);

    protected List<XORHexString> getPaddedXORs(Collection<HexString> ciphertexts) {
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

        ArrayList<XORHexString> coveredXORs = new ArrayList<>();
        for (HexString ciphertextA : paddedCiphertexts) {
            for (HexString ciphertextB : paddedCiphertexts) {
                if (ciphertextA.equals(ciphertextB)) {
                    continue;
                }
                XORHexString xor = ciphertextA.xor(ciphertextB);
                if (!coveredXORs.contains(xor)) {
                    coveredXORs.add(xor);
                }
            }
        }
        return coveredXORs;
    }

    public BasicHexString determineKeyByDoingSomething(Collection<HexString> ciphertexts) {
        List<XORHexString> paddedXORs = getPaddedXORs(ciphertexts);

        int maxLength = paddedXORs.getFirst().getLength();
        char[] keyGuess = new char[maxLength];
        // Initialize with '0' (null byte for XOR)
        Arrays.fill(keyGuess, '0');

        for (BasicHexString xor : paddedXORs) {
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
        BasicHexString guessedKey = new BasicHexString(new String(keyGuess));
        System.out.println("Guessed Key:");
        System.out.println(guessedKey.convertToString());
        return guessedKey;
    }

    private static boolean isLikelySpaceOrLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ';
    }
}

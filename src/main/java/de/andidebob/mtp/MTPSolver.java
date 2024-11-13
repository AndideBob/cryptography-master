package de.andidebob.mtp;

import de.andidebob.otp.hexstring.HexString;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MTPSolver {

    public abstract String[] solve(Collection<HexString> ciphertexts, String cipherTextToDecipher);

    protected List<HexString> getPaddedXORs(Collection<HexString> ciphertexts) {
        int maxLength = ciphertexts.stream()
                .max(Comparator.comparingInt(HexString::length))
                .orElseThrow(() -> new RuntimeException("Expected at least one element!"))
                .length();

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
}

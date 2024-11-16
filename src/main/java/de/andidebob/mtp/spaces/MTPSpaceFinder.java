package de.andidebob.mtp.spaces;

import de.andidebob.otp.hexstring.HexString;

import java.util.ArrayList;
import java.util.List;

public class MTPSpaceFinder {

    public static List<HexString> findCipherTextsWithSpaceAtIndex(List<HexString> fittingCiphertexts, int currentIndex) {
        ArrayList<HexString> possibleCiphertexts = new ArrayList<>();
        // Check if all Ciphertexts have an XOR value at the current character that would indicate that ciphertext has a space here
        // Check only for letters
        for (HexString cipherText : fittingCiphertexts) {

            if (fittingCiphertexts.stream()
                    .map(c -> (char) (c.charAt(currentIndex) ^ cipherText.charAt(currentIndex)))
                    .allMatch(MTPSpaceMap.instance::isAlphabeticXOR)) {
                possibleCiphertexts.add(cipherText);
            }
        }
        if (!possibleCiphertexts.isEmpty()) {
            return possibleCiphertexts;
        }
        // Check for punctuation
        for (HexString cipherText : fittingCiphertexts) {

            if (fittingCiphertexts.stream()
                    .map(c -> (char) (c.charAt(currentIndex) ^ cipherText.charAt(currentIndex)))
                    .allMatch(c -> MTPSpaceMap.instance.isAlphabeticXOR(c) || MTPSpaceMap.instance.isPunctuationXOR(c))) {
                possibleCiphertexts.add(cipherText);
            }
        }
        if (!possibleCiphertexts.isEmpty()) {
            return possibleCiphertexts;
        }
        // Check for numbers
        for (HexString cipherText : fittingCiphertexts) {

            if (fittingCiphertexts.stream()
                    .map(c -> (char) (c.charAt(currentIndex) ^ cipherText.charAt(currentIndex)))
                    .allMatch(c -> MTPSpaceMap.instance.isAlphabeticXOR(c) || MTPSpaceMap.instance.isPunctuationXOR(c) || MTPSpaceMap.instance.isNumberXOR(c))) {
                possibleCiphertexts.add(cipherText);
            }
        }
        return possibleCiphertexts;
    }
}

package de.andidebob.mtp.spaces;

import de.andidebob.language.LanguageAnalyzer;
import de.andidebob.language.LanguageModel;
import de.andidebob.otp.hexstring.HexString;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MTPSolver {

    private final LanguageAnalyzer languageAnalyzer;

    public MTPSolver(LanguageModel languageModel) {
        this.languageAnalyzer = new LanguageAnalyzer(languageModel);
    }


    public HashMap<HexString, String> solve(HexString[] ciphertextArray) {
        HashMap<HexString, String> result = new HashMap<>();
        for (HexString hexString : ciphertextArray) {
            result.put(hexString, initializeAsClear(hexString));
        }
        final int maxCiphertextLength = result.keySet()
                .stream()
                .mapToInt(HexString::length)
                .max()
                .orElseThrow(() -> new RuntimeException("Max CipherLength could not be determined!"));
        for (int i = 0; i < maxCiphertextLength; i++) {
            int currentIndex = i;
            List<HexString> fittingCiphertexts = result.keySet().stream().filter(c -> c.length() > currentIndex).toList();
            List<HexString> cipherTextsWithSpace = MTPSpaceFinder.findCipherTextsWithSpaceAtIndex(fittingCiphertexts, currentIndex);
            HexString bestHexString = findBestHexStringWithSpace(cipherTextsWithSpace, currentIndex, result);
            if (bestHexString != null) {
                for (HexString hexString : result.keySet()) {
                    if (result.get(hexString).length() > currentIndex) {
                        char xor = (char) (bestHexString.charAt(currentIndex) ^ hexString.charAt(currentIndex));

                        StringBuilder s = new StringBuilder(result.get(hexString));
                        if (MTPSpaceMap.instance.isSpaceXOR(xor)) {
                            s.setCharAt(currentIndex, MTPSpaceMap.instance.getMappingFor(xor));
                        }
                        result.put(hexString, s.toString());
                    }
                }
            }
        }

        return result;
    }

    private HexString findBestHexStringWithSpace(List<HexString> cipherTextsWithSpace, final int currentIndex, final HashMap<HexString, String> result) {
        if (cipherTextsWithSpace.size() <= 1) {
            return cipherTextsWithSpace.stream().findFirst().orElse(null);
        }
        HashMap<HexString, Double> values = new HashMap<>();
        System.out.println("Evaluating:");
        for (HexString cipherWithSpace : cipherTextsWithSpace) {
            double deviation = 0;
            for (HexString hexString : result.keySet()) {
                if (result.get(hexString).length() > currentIndex) {
                    char xor = (char) (cipherWithSpace.charAt(currentIndex) ^ hexString.charAt(currentIndex));

                    StringBuilder s = new StringBuilder(result.get(hexString));
                    if (MTPSpaceMap.instance.isSpaceXOR(xor)) {
                        s.setCharAt(currentIndex, MTPSpaceMap.instance.getMappingFor(xor));
                    }
                    deviation += languageAnalyzer.getLanguageDeviationScore(s.substring(0, currentIndex));
                    System.out.println(s.substring(0, currentIndex));
                }
            }
            values.put(cipherWithSpace, deviation);
            System.out.println("Deviation: " + deviation);
        }
        return values.entrySet().stream().min(Comparator.comparingDouble(HashMap.Entry::getValue)).orElse(null).getKey();
    }

    public String initializeAsClear(HexString cipher) {
        return "?".repeat(cipher.length());
    }
}

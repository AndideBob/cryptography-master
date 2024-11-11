package de.andidebob.mtp.cribdrag;

import de.andidebob.language.BigramAnalyzer;
import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.mtp.MTPSolver;
import de.andidebob.otp.hexstring.BasicHexString;
import de.andidebob.otp.hexstring.HexString;
import de.andidebob.otp.hexstring.XORHexString;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class MTPCribDragSolver extends MTPSolver {

    private static final double BIGRAM_ANALYSIS_THRESHOLD = -2.0;

    private final HashMap<HexString, HexString> cipherPlainTextMap = new HashMap<>();
    private final BigramAnalyzer bigramAnalyzer = new BigramAnalyzer(new EnglishLanguageModel());

    @Override
    public String[] solve(Collection<HexString> ciphertexts, String cipherTextToDecipher) {
        analyze(getPaddedXORs(ciphertexts));
        return new String[0];
    }

    public void analyze(List<XORHexString> paddedXORs) {
        final int textLength = paddedXORs.getFirst().convertToString().length();
        cipherPlainTextMap.clear();
        HexString key = BasicHexString.empty(paddedXORs.getFirst().getLength());

        for (XORHexString xor : paddedXORs) {
            if (!cipherPlainTextMap.containsKey(xor.getOriginalA())) {
                cipherPlainTextMap.put(xor.getOriginalA(), xor.getOriginalA().xor(key));
            }
            if (!cipherPlainTextMap.containsKey(xor.getOriginalB())) {
                cipherPlainTextMap.put(xor.getOriginalB(), xor.getOriginalB().xor(key));
            }
        }

        for (CribDragPattern pattern : CribDragPattern.values()) {
            String fillerPattern = pattern.asFiller();
            for (XORHexString paddedXOR : paddedXORs) {
                int latestStartIndex = getLatestStartIndex(paddedXOR);
                for (int i = latestStartIndex; i < textLength - fillerPattern.length(); i++) {
                    String patternString = i > latestStartIndex ? fillerPattern : pattern.asStarter();
                    if (makesSense(patternString, i, paddedXOR)) {
                        System.out.println("pattern '" + patternString + "' makes sense!");
                    }
                }
            }
        }
    }

    private boolean makesSense(String pattern, int index, XORHexString xor) {
        int patternLength = pattern.length();
        HexString xorPart = getCipherSubstring(xor, index, patternLength);
        HexString patternHex = BasicHexString.ofString(pattern);
        HexString reverseXOR = patternHex.xor(xorPart);
        if (reverseXOR.convertToString().equals(pattern)) {
            return false;
        }
        double score = bigramAnalyzer.calculateScore(reverseXOR.convertToString());
        if (score >= BIGRAM_ANALYSIS_THRESHOLD) {
            System.out.println("pattern '" + pattern + "' makes-sense and resulted in '" + reverseXOR.convertToString() + "'! (Score: " + score + ")");
            return true;
        }
        return false;
    }

    private static HexString getCipherSubstring(HexString hexString, int index, int patternLength) {
        return BasicHexString.ofString(hexString.convertToString().substring(index, index + patternLength));
    }

    private static int getLatestStartIndex(XORHexString xor) {
        return xor.convertToString().length() - Math.min(xor.getOriginalA().convertToString().trim().length(), xor.getOriginalB().convertToString().trim().length());
    }
}

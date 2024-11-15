package de.andidebob.mtp.spaces;

import de.andidebob.mtp.MTPSolver;
import de.andidebob.otp.hexstring.HexString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MTPSpaceSolver extends MTPSolver {
    @Override
    public String[] solve(Collection<HexString> ciphertexts) {
        List<String> result = new ArrayList<>(initializeClears(ciphertexts));
        final int maxCiphertextLength = ciphertexts.stream().mapToInt(HexString::length).max().getAsInt();
        try {
            for (int i = 0; i < maxCiphertextLength; i++) {
                int currentIndex = i;
                List<HexString> fittingCiphertexts = ciphertexts.stream().filter(c -> c.length() > currentIndex).toList();
                for (HexString cipherTextToCheck : fittingCiphertexts) {
                    // If character at current index is space for all chipherTexts
                    if (fittingCiphertexts.stream().allMatch(c -> isPotentialSpace(c.charAt(currentIndex), cipherTextToCheck.charAt(currentIndex)))) {
                        for (int j = 0; j < fittingCiphertexts.size(); j++) {
                            if (!result.get(j).isEmpty() && currentIndex < result.get(j).length()) {
                                char xor = (char) (cipherTextToCheck.charAt(currentIndex) ^ fittingCiphertexts.get(j).charAt(currentIndex));

                                StringBuilder s = new StringBuilder(result.get(j));
                                if (MTPSpaceMap.instance.isCharacterResultOfXOrWithSpace(xor)) {
                                    s.setCharAt(currentIndex, MTPSpaceMap.instance.getMappingFor(xor));
                                }
                                result.set(j, s.toString());
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toArray(String[]::new);
    }

    public List<String> initializeClears(Collection<HexString> ciphers) {
        return ciphers.stream().map(c -> "?".repeat(c.length())).toList();
    }

    private boolean isPotentialSpace(char charA, char charB) {
        char xor = (char) (charA ^ charB);
        return MTPSpaceMap.instance.isCharacterResultOfXOrWithSpace(xor);
    }
}

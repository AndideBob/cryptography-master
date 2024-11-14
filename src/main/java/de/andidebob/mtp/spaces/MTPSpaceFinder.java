package de.andidebob.mtp.spaces;

import de.andidebob.otp.hexstring.HexString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MTPSpaceFinder {

    public List<SpaceCharacterProbability> findSpaces(Collection<HexString> ciphertexts) {
        ArrayList<SpaceCharacterProbability> result = new ArrayList<>();
        final int numberOfCiphertexts = ciphertexts.size();
        for (HexString cipherTextToCheck : ciphertexts) {
            HashMap<Integer, Integer> probabilities = new HashMap<>();
            for (HexString otherCiphertext : ciphertexts) {
                String xor = cipherTextToCheck.xor(otherCiphertext).toString();
                int startIndex = xor.length() - Math.min(cipherTextToCheck.toString().length(), otherCiphertext.toString().length());
                for (int i = startIndex; i < xor.length(); i++) {
                    if (MTPSpaceMap.instance.isCharacterResultOfXOrWithSpace(xor.charAt(i))) {
                        if (!probabilities.containsKey(i)) {
                            probabilities.put(i, 1);
                        } else {
                            probabilities.put(i, probabilities.get(i) + 1);
                        }
                    }
                }
            }
            probabilities.forEach((index, count) -> {
                result.add(new SpaceCharacterProbability(cipherTextToCheck, index, 1.0 * count / numberOfCiphertexts));
            });

        }
        return result;
    }


}

package de.andidebob.mtp.spaces;

import de.andidebob.mtp.MTPSolver;
import de.andidebob.otp.hexstring.HexString;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class MTPSpaceSolver extends MTPSolver {
    @Override
    public String[] solve(Collection<HexString> ciphertexts, String cipherTextToDecipher) {
        final int cipherLength = ciphertexts.stream()
                .map(HexString::length).max(Integer::compare)
                .orElseThrow(() -> new RuntimeException("Cant solve without ciphertexts!"));
        List<HexString> paddedCiphers = ciphertexts.stream().map(c -> c.padToLength(cipherLength)).toList();
        for (HexString padded : paddedCiphers) {
            System.out.println(padded);
        }
        MTPSpaceFinder spaceFinder = new MTPSpaceFinder();
        List<SpaceCharacterProbability> spaces = spaceFinder.findSpaces(ciphertexts);
        String key = determineKey(spaces, cipherLength);
        HexString keyHex = HexString.fromString(key);
        System.out.println(keyHex);
        for (HexString ciphertext : ciphertexts) {
            System.out.println(ciphertext.xor(keyHex).toString());
        }
        return new String[0];
    }

    private String determineKey(List<SpaceCharacterProbability> spaces, final int cipherLength) {
        Character[] keyCharacters = new Character[cipherLength];
        spaces.sort(Comparator.comparingDouble(SpaceCharacterProbability::probability).reversed());
        try {
            for (SpaceCharacterProbability space : spaces) {
                if (keyCharacters[space.characterIndex()] == null) {
                    Character c = space.getHexStringChar();
                    if (c != null) {
                        keyCharacters[space.characterIndex()] = (char) (c ^ ' ');
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        for (Character keyCharacter : keyCharacters) {
            builder.append(keyCharacter != null ? keyCharacter : '0');
        }
        return builder.toString();
    }
}

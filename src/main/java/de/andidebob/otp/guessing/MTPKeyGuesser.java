package de.andidebob.otp.guessing;

import de.andidebob.mtp.MTPKeyStorage;
import de.andidebob.otp.StringXORMap;
import de.andidebob.otp.hexstring.HexString;
import de.andidebob.otp.hexstring.XORHexString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MTPKeyGuesser {

    private final HashSet<XORHexString> xors;

    public MTPKeyGuesser(List<XORHexString> paddedXORs) {
        this.xors = new HashSet<>(paddedXORs);
    }

    public MTPKeyStorage determinePossibleKeyCharacters(boolean useExtendedAscii) {
        MTPKeyStorage keyGuess = new MTPKeyStorage();

        for (XORHexString xor : xors) {
            for (int i = 0; i < xor.getLength() - 1; i += 2) {
                HexString current = xor.getHexCharAt(i);
                // Ignore if xor is the same as one of the ciphertexts (because it was XORed with 00)
                if (current.equals(xor.getOriginalA().getHexCharAt(i)) || current.equals(xor.getOriginalB().getHexCharAt(i))) {
                    continue;
                }
                Set<StringXORMap.CharacterMapping> possibleMappings = StringXORMap.getInstance().getPossibleMappings(current, useExtendedAscii);
                int plainTextCharPos = i / 2;
                char currentCharA = xor.getOriginalA().convertToString().charAt(plainTextCharPos);
                char currentCharB = xor.getOriginalB().convertToString().charAt(plainTextCharPos);
                Set<Character> possibleKeyCharacters = possibleMappings.stream()
                        .flatMap(cm -> getXORedCharacters(cm, currentCharA, currentCharB))
                        .collect(Collectors.toSet());
                keyGuess.mergePossibilities(plainTextCharPos, possibleKeyCharacters);
            }
        }
        return keyGuess;
    }

    private Stream<Character> getXORedCharacters(StringXORMap.CharacterMapping mapping, char a, char b) {
        HashSet<Character> characters = new HashSet<>();
        characters.add((char) (a ^ mapping.a()));
        characters.add((char) (a ^ mapping.b()));
        characters.add((char) (b ^ mapping.a()));
        characters.add((char) (b ^ mapping.b()));
        return characters.stream();
    }

    public void solveKey(Set<HexString> ciphertexts, MTPKeyStorage keyGuess, int requiredKeyLength) {


    }
}

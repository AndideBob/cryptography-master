package de.andidebob.mtp.guessing;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.language.LanguageModel;
import de.andidebob.mtp.MTPSolver;
import de.andidebob.otp.hexstring.BasicHexString;
import de.andidebob.otp.hexstring.HexString;
import de.andidebob.otp.hexstring.XORHexString;

import java.util.*;

public class MTPKeyGuessSolver extends MTPSolver {

    private final LanguageModel languageModel = new EnglishLanguageModel();

    @Override
    public String[] solve(Collection<HexString> ciphertexts, String cipherTextToDecipher) {
        HexString guessedKey = determineKeyByGuessingProbability(ciphertexts, cipherTextToDecipher.length());
        try {
            for (HexString ciphertext : ciphertexts) {
                System.out.println(guessedKey.xor(ciphertext).convertToString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[0];
    }

    public HexString determineKeyByGuessingProbability(Collection<HexString> ciphertexts, int requiredKeyLength) {
        List<XORHexString> paddedXORs = getPaddedXORs(ciphertexts);

        MTPKeyStorage possibleKeyCharacters = determinePossibleKeyCharacters(paddedXORs, requiredKeyLength);

        return determineBestKey(ciphertexts, possibleKeyCharacters, requiredKeyLength);
    }

    private MTPKeyStorage determinePossibleKeyCharacters(List<XORHexString> paddedXORs, int requiredKeyLength) {
        System.out.println("Determining possible Key Characters...");
        MTPKeyGuesser keyGuesser = new MTPKeyGuesser(paddedXORs);

        MTPKeyStorage possibleKeyCharacters = keyGuesser.determinePossibleKeyCharacters(false);

        int keyLength = possibleKeyCharacters.getKeyLength();
        int earliestCharacterNeeded = keyLength - requiredKeyLength;
        boolean needExtendedAscii = false;
        for (int i = keyLength - 1; i >= earliestCharacterNeeded; i--) {
            if (possibleKeyCharacters.getCharactersAt(i).isEmpty()) {
                needExtendedAscii = true;
            }
        }
        if (needExtendedAscii) {
            System.out.println("Extended Ascii needed, recomputing...");
            return keyGuesser.determinePossibleKeyCharacters(true);
        }
        return possibleKeyCharacters;
    }

    private HexString determineBestKey(Collection<HexString> ciphertexts, MTPKeyStorage possibleKeyCharacters, int requiredKeyLength) {
        Map<String, Double> bigrams = languageModel.getBigramFrequencyMap();
        final int startIndex = possibleKeyCharacters.getKeyLength() - requiredKeyLength;
        StringBuilder keyBuilder = new StringBuilder();
        for (int index = startIndex; index < possibleKeyCharacters.getKeyLength() - 1; index++) {
            HashMap<Character, Double> scores = new HashMap<>();
            Set<Character> keyCharacters = possibleKeyCharacters.getCharactersAt(index);
            for (Character keyCharacter : keyCharacters) {
                double score = 0;
                for (HexString ciphertext : ciphertexts) {
                    HexString padded = ciphertext.padToLength(possibleKeyCharacters.getKeyLength() * 2);
                    HexString hexCharA = padded.getHexCharAt(index * 2);
                    HexString hexCharB = padded.getHexCharAt((index + 1) * 2);
                    String bigram = hexCharA.xor(BasicHexString.ofChar(keyCharacter)).convertToString() + hexCharB.xor(BasicHexString.ofChar(keyCharacter)).convertToString();
                    if (!bigrams.containsKey(bigram)) {
                        System.out.println("Bigram not found: " + bigram);
                    } else {
                        score += bigrams.get(bigram);
                    }
                }
                scores.put(keyCharacter, score);
            }
            System.out.println("Character scores at index " + index + ":");
            System.out.println(scores);
            keyBuilder.append(scores.entrySet()
                    .stream()
                    .max(Comparator.comparingDouble(Map.Entry::getValue))
                    .orElseThrow(() -> new RuntimeException("Expected at least one result!"))
                    .getKey());
        }
        System.out.println(keyBuilder);
        return BasicHexString.ofString(keyBuilder.toString());
    }
}

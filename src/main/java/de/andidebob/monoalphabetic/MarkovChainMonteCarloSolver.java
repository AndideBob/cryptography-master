package de.andidebob.monoalphabetic;

import de.andidebob.frequency.CharacterFrequency;
import de.andidebob.frequency.CharacterFrequencyResult;
import de.andidebob.language.BiGram;
import de.andidebob.language.LanguageModel;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Random;

public class MarkovChainMonteCarloSolver extends MonoalphabeticSubstitutor {

    private final Random random = new Random();

    public MarkovChainMonteCarloSolver(LanguageModel languageModel) {
        super(languageModel);
    }

    @Override
    public String decryptMessage(String message) {
        System.out.println("Encrypted message: ");
        System.out.println(message);
        final CharacterFrequencyResult frequencyResult = frequencyAnalyzer.analyze(message);
        final HashSet<AlphabetKey> triedKeys = new HashSet<>();
        AlphabetKey currentKey = new AlphabetKey(languageModel, frequencyResult);
        String currentPlainText = decrypt(message, currentKey);
        double currentScore = evaluate(currentPlainText);
        System.out.println("Current Iteration (" + currentScore + "):");
        System.out.println(currentPlainText);
        triedKeys.add(currentKey);
        for (int i = 0; i < 1000; i++) {
            System.out.println("NewIteration:");
            AlphabetKey newKey = getModifiedKey(currentKey, triedKeys);
            triedKeys.add(newKey);
            String newPlainText = decrypt(message, newKey);
            double newScore = evaluate(newPlainText);
            if (newScore > currentScore) {
                currentPlainText = newPlainText;
                currentKey = newKey;
            }
            System.out.println("Score (" + newScore + "):");
            System.out.println(newPlainText);
        }
        return currentPlainText;
    }

    private AlphabetKey getModifiedKey(AlphabetKey currentKey, final HashSet<AlphabetKey> triedKeys) {
        AlphabetKey newKey = currentKey.copy();
        int numberOfSwaps = 1;
        int numberOfTries = 0;
        while (triedKeys.contains(newKey)) {
            newKey = currentKey.copy();
            numberOfTries++;
            for (int i = 0; i < numberOfSwaps; i++) {
                int a = 0;
                int b = 0;
                while (a == b) {
                    a = random.nextInt(26);
                    b = Math.min(25, Math.max(0, a - 5 + random.nextInt(11)));
                }
                char fromA = languageModel.getByRarityIndex(a).character();
                char fromB = languageModel.getByRarityIndex(b).character();
                char toA = newKey.map(fromA);
                char toB = newKey.map(fromB);
                newKey.overrideMapping(fromA, toB);
                newKey.overrideMapping(fromB, toA);
                if (!triedKeys.contains(newKey)) {
                    System.out.println("Swapping " + fromA + " -> " + toA + " and " + fromB + " -> " + toB);
                }
            }
            if (numberOfTries > 1000) {
                numberOfSwaps++;
            }
        }
        return newKey;
    }

    private double evaluate(String message) {
        CharacterFrequencyResult frequencyResult = frequencyAnalyzer.analyze(message);
        double singleLetterResult = 0.0;
        for (CharacterFrequency frequency : frequencyResult.getFrequencies()) {
            singleLetterResult += Math.log(languageModel.getForCharacters(frequency.character()).percentage()) * frequency.amount();
        }
        double bigramResult = 0.0;
        for (BiGram biGram : languageModel.getBigrams()) {
            int instancesFound = StringUtils.countMatches(message, biGram.raw().toUpperCase());
            bigramResult += Math.log(biGram.frequency()) * instancesFound;
        }
        System.out.println("SLR: " + singleLetterResult);
        System.out.println("BGR: " + bigramResult);
        return singleLetterResult - 10 * bigramResult;
    }
}

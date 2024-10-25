package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.vigenere.VigenereKeyFinder;
import de.andidebob.vigenere.kasiski.KasiskiAnalyzer;
import de.andidebob.vigenere.kasiski.KeyLengthProbabilityResult;

import java.util.Comparator;
import java.util.List;

public class TaskVigenere implements TaskHandler {

    private static final int KNOWN_MAX_KEY_LENGTH = 20;

    @Override
    public void handleInput(String[] lines) {
        VigenereKeyFinder keyFinder = new VigenereKeyFinder(new EnglishLanguageModel());
        // TODO Doesn't seem to work (VIGENERE -> abc -> VJIEOGRF)
        keyFinder.findKey("VJIEOGRF", 3);
    }

    private void doStuff(String[] lines) {
        final String fullString = String.join("\n", lines);

        KasiskiAnalyzer kasiskiAnalyzer = new KasiskiAnalyzer();
        List<KeyLengthProbabilityResult> keyLengthProbabilityResults = kasiskiAnalyzer.determineKeyLength(fullString, KNOWN_MAX_KEY_LENGTH);

        Integer[] keyLengthsByProbability = keyLengthProbabilityResults.stream()
                .sorted(Comparator.comparingDouble(KeyLengthProbabilityResult::probability).reversed())
                .map(KeyLengthProbabilityResult::keylength)
                .toArray(Integer[]::new);

        VigenereKeyFinder keyFinder = new VigenereKeyFinder(new EnglishLanguageModel());
        keyFinder.findKey(fullString, 10);
        for (int i = 0; i < keyLengthsByProbability.length; i++) {
            //System.out.println("Decrypt with keyLength '" + keyLengthsByProbability[i] + "':");

        }
        // TODO: Frequency Analysis to LanguageModel - Repeat for other KeyLengths
    }
}

package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.vigenere.VigenereDecrypter;
import de.andidebob.vigenere.VigenereKeyFinder;
import de.andidebob.vigenere.kasiski.KasiskiAnalyzer;
import de.andidebob.vigenere.kasiski.KeyLengthProbabilityResult;

import java.util.Comparator;
import java.util.List;

public class TaskVigenere implements TaskHandler {

    private static final int KNOWN_MAX_KEY_LENGTH = 20;

    @Override
    public void handleInput(String[] lines) {
        final String fullString = String.join("\n", lines);

        KasiskiAnalyzer kasiskiAnalyzer = new KasiskiAnalyzer();
        List<KeyLengthProbabilityResult> keyLengthProbabilityResults = kasiskiAnalyzer.determineKeyLength(fullString, KNOWN_MAX_KEY_LENGTH);

        Integer[] keyLengthsByProbability = keyLengthProbabilityResults.stream()
                .sorted(Comparator.comparingDouble(KeyLengthProbabilityResult::probability).reversed())
                .map(KeyLengthProbabilityResult::keylength)
                .toArray(Integer[]::new);

        VigenereKeyFinder keyFinder = new VigenereKeyFinder(new EnglishLanguageModel());
        VigenereDecrypter decrypter = new VigenereDecrypter();
        for (Integer integer : keyLengthsByProbability) {
            String key = keyFinder.findKey(fullString, integer);
            System.out.println("Trying key: " + key);
            System.out.println(decrypter.decrypt(fullString, key));
            // TODO: Frequency Analysis to LanguageModel
        }
    }
}

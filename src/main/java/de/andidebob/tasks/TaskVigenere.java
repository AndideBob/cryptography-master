package de.andidebob.tasks;

import de.andidebob.vigenere.kasiski.KasiskiAnalyzer;
import de.andidebob.vigenere.kasiski.KeyLengthProbabilityResult;

import java.util.Comparator;
import java.util.List;

public class TaskVigenere implements TaskHandler {

    private static final int KNOWN_MAX_KEY_LENGTH = 20;

    @Override
    public void handleInput(String[] lines) {
        KasiskiAnalyzer kasiskiAnalyzer = new KasiskiAnalyzer();
        List<KeyLengthProbabilityResult> keyLengthProbabilityResults = kasiskiAnalyzer.determineKeyLength(String.join("\n", lines), KNOWN_MAX_KEY_LENGTH);

        System.out.println("Keylength propabilities:");
        keyLengthProbabilityResults.stream().sorted(Comparator.comparingDouble(KeyLengthProbabilityResult::probability)).forEach(entry -> System.out.println(entry.keylength() + "|" + entry.probability()));
    }
}

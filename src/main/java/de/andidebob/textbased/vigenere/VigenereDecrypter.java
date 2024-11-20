package de.andidebob.textbased.vigenere;

import de.andidebob.textbased.frequency.CharacterFrequencyResult;
import de.andidebob.textbased.frequency.FrequencyAnalyzer;
import de.andidebob.textbased.language.LanguageModel;
import de.andidebob.textbased.vigenere.kasiski.KasiskiAnalyzer;
import de.andidebob.textbased.vigenere.kasiski.KeyLengthProbabilityResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VigenereDecrypter {

    private final KasiskiAnalyzer kasiskiAnalyzer = new KasiskiAnalyzer();
    private final FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.lettersOnly();
    private final LanguageModel languageModel;
    private final VigenereKeyFinder keyFinder;

    public VigenereDecrypter(LanguageModel languageModel) {
        this.languageModel = languageModel;
        keyFinder = new VigenereKeyFinder(languageModel);
    }

    public VignereDecryptionResult decrypt(String input) {
        return this.decrypt(input, input.length());
    }

    public VignereDecryptionResult decrypt(String input, int knownMaxKeyLength) {
        List<KeyLengthProbabilityResult> keyLengthProbabilityResults = kasiskiAnalyzer.determineKeyLength(input, knownMaxKeyLength);

        Integer[] keyLengthsByProbability = keyLengthProbabilityResults.stream()
                .sorted(Comparator.comparingDouble(KeyLengthProbabilityResult::probability).reversed())
                .map(KeyLengthProbabilityResult::keylength)
                .toArray(Integer[]::new);

        List<VignereDecryptionResult> decryptionResults = new ArrayList<>();

        for (Integer integer : keyLengthsByProbability) {
            String key = keyFinder.findKey(input, integer);
            String result = applyKey(input, key);
            CharacterFrequencyResult frequencyResult = frequencyAnalyzer.analyze(result);
            double deviationFromLanguageModel = frequencyResult.getDeviationFromLanguageModel(languageModel);
            decryptionResults.add(new VignereDecryptionResult(key, result, deviationFromLanguageModel));
            // For increased performance but less accuracy enable
            // if(deviationFromLanguageModel < 0.05) { break; }
        }
        return decryptionResults.stream()
                .min(Comparator.comparingDouble(VignereDecryptionResult::languageDeviation))
                .orElseThrow(() -> new RuntimeException("Expected to have at least one Result!"));
    }

    private String applyKey(String input, String key) {
        StringBuilder result = new StringBuilder();
        char[] characters = input.toCharArray();
        int keyIndex = 0;
        for (char character : characters) {
            if (("" + character).matches("[a-zA-Z]")) {
                result.append(VigenereTable.instance.map(key.charAt(keyIndex), character));
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
}

package de.andidebob.caesar;

import de.andidebob.alphabet.AlphabetKey;
import de.andidebob.frequency.FrequencyAnalyzer;
import de.andidebob.language.LanguageModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CaesarAnalyzer {

    private final FrequencyAnalyzer analyzer = FrequencyAnalyzer.lettersOnly();
    private final LanguageModel languageModel;

    public String unshiftCaesar(String input) {
        String result = input.toUpperCase();
        double currentDeviation = analyzer.analyze(input).getDeviationFromLanguageModel(languageModel);
        for (int i = 1; i < 26; i++) {
            AlphabetKey key = AlphabetKey.withCaesarShift(i);
            String mappedLine = key.mapAll(input.toUpperCase());
            double newDeviation = analyzer.analyze(mappedLine).getDeviationFromLanguageModel(languageModel);
            if (newDeviation < currentDeviation) {
                currentDeviation = newDeviation;
                result = mappedLine;
            }
        }
        return result;
    }
}

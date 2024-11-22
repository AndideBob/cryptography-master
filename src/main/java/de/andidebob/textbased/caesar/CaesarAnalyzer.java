package de.andidebob.textbased.caesar;

import de.andidebob.textbased.alphabet.AlphabetKey;
import de.andidebob.textbased.frequency.FrequencyAnalyzer;
import de.andidebob.textbased.language.LanguageModel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;

@RequiredArgsConstructor
public class CaesarAnalyzer {

    private final FrequencyAnalyzer analyzer = FrequencyAnalyzer.lettersOnly();
    private final LanguageModel languageModel;

    public String unshiftCaesar(String input) {
        ArrayList<CaesarShiftResult> result = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            AlphabetKey key = AlphabetKey.withCaesarShift(i);
            String mappedLine = key.mapAll(input);
            double deviationFromLanguageModel = analyzer.analyze(mappedLine).getDeviationFromLanguageModel(languageModel);
            result.add(new CaesarShiftResult(i, mappedLine, deviationFromLanguageModel));
        }
        return result.stream()
                .min(Comparator.comparingDouble(CaesarShiftResult::languageDeviation))
                .orElseThrow(() -> new RuntimeException("Expected at least one result!"))
                .result;
    }

    private record CaesarShiftResult(int shift, String result, double languageDeviation) {

    }
}

package de.andidebob.textbased.language;

import de.andidebob.textbased.frequency.CharacterFrequencyResult;
import de.andidebob.textbased.frequency.FrequencyAnalyzer;

public class LanguageAnalyzer {

    private static final String PUNCTUATION_CHARACTERS = "!.,;:'\"?-";

    private final LanguageModel languageModel;
    private final FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.lettersOnly();
    private final BigramAnalyzer bigramAnalyzer;

    public LanguageAnalyzer(LanguageModel languageModel) {
        this.languageModel = languageModel;
        this.bigramAnalyzer = new BigramAnalyzer(languageModel);
    }

    public double getLanguageDeviationScore(String text) {
        CharacterFrequencyResult characterFrequencyResult = frequencyAnalyzer.analyze(text);
        double baseScore = characterFrequencyResult.getDeviationFromLanguageModel(languageModel);
        double doublePunctuationFactor = 1 + (0.5 * countDoublePunctuation(text));
        double score = baseScore * doublePunctuationFactor;

        score -= bigramAnalyzer.calculateScore(text);

        return score;
    }

    private int countDoublePunctuation(String text) {
        int count = 0;
        for (int i = 0; i < text.length() - 1; i++) {
            if (isPunctuation(text.charAt(i)) && isPunctuation(text.charAt(i + 1))) {
                count++;
            }
        }
        return count;
    }

    private boolean isPunctuation(char c) {
        return PUNCTUATION_CHARACTERS.indexOf(c) >= 0;
    }
}

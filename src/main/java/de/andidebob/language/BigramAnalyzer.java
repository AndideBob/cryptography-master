package de.andidebob.language;

import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class BigramAnalyzer {

    private final LanguageModel languageModel;
    private static final Pattern ONE_PUNCTUATION_PATTERN = Pattern.compile("^[\\p{Punct}][\\w]$|^[\\w][\\p{Punct}]$");
    private static final Pattern NON_ASCII_PATTERN = Pattern.compile("[^\\x00-\\x7F]");
    private static final double WORST_SCORE = -100000.0;

    public boolean isScoreGreaterThanThreshold(String input, double threshold) {
        return threshold <= calculateScore(input);
    }

    public double calculateScore(String input) {
        if (containsNonAscii(input)) {
            return WORST_SCORE;
        }
        String text = input.toUpperCase();
        double score = 0.0;
        int bigramCount = 0;
        int punctuationCount = 0;

        for (int i = 0; i < text.length() - 1; i++) {
            String bigram = text.substring(i, i + 2); // Extract each bigram
            Double frequency = languageModel.getBigramFrequencyMap().get(bigram);

            if (frequency != null) {
                // Use log frequencies to avoid very small numbers
                score += Math.log(frequency);
                bigramCount++;
            } else {
                // For spaces and puctuation add small penalties
                if (containsOnlyOnePunctuationCharacter(bigram)) {
                    if (punctuationCount++ > 0) { // penalty for multiple punctuation
                        score += Math.log(0.0001);
                    }
                } else {
                    score += Math.log(0.0001);
                }
            }
        }

        return bigramCount > 0 ? score / bigramCount : WORST_SCORE;
    }

    public boolean containsOnlyOnePunctuationCharacter(String bigram) {
        if (bigram.length() != 2) {
            throw new RuntimeException("bigram should consist only of 2 characters!");
        }
        return ONE_PUNCTUATION_PATTERN.matcher(bigram).matches();
    }

    public boolean containsNonAscii(String text) {
        if (text == null) {
            return false;
        }
        return NON_ASCII_PATTERN.matcher(text).find();
    }
}

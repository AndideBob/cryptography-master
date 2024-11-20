package de.andidebob.textbased.vigenere.kasiski;

import de.andidebob.textbased.vigenere.kasiski.primefactor.KasiskiCalculator;
import de.andidebob.textbased.vigenere.kasiski.substrings.SubStringInstance;
import de.andidebob.textbased.vigenere.kasiski.substrings.SubstringReoccurrenceDetector;

import java.util.*;
import java.util.stream.Collectors;

public class KasiskiAnalyzer {

    private final static int MIN_SUBSTRING_LENGTH = 3;
    private final static int MAX_SUBSTRING_LENGTH = 15;
    private final SubstringReoccurrenceDetector substringReoccurrenceDetector = new SubstringReoccurrenceDetector();

    public List<KeyLengthProbabilityResult> determineKeyLength(String input) {
        return determineKeyLength(input, input.length());
    }

    public List<KeyLengthProbabilityResult> determineKeyLength(String input, int knownMaximumKeyLength) {
        List<SubStringInstance> reoccurringSubstrings = substringReoccurrenceDetector.findReoccurringSubstrings(input, MIN_SUBSTRING_LENGTH, MAX_SUBSTRING_LENGTH);

        if (reoccurringSubstrings.isEmpty()) {
            return getAllKeyLengths(knownMaximumKeyLength);
        }

        int longestSubstring = reoccurringSubstrings
                .stream()
                .map(i -> i.getString().length())
                .max(Comparator.comparingInt(Integer::intValue))
                .orElseThrow(() -> new RuntimeException("At least one substring should exist!"));

        Set<Integer> distancesBetweenReoccurrences = getDistancesBetweenSubstringReoccurrences(reoccurringSubstrings);

        KasiskiCalculator kasiskiCalculator = new KasiskiCalculator();
        Map<Integer, Integer> commonMultiples = kasiskiCalculator.getCommonMultiples(distancesBetweenReoccurrences);

        // Remove all instances with less characters than the longest reoccurrence
        // TODO Determine if this is valid
        commonMultiples.entrySet().removeIf(entry -> entry.getKey() < longestSubstring);
        // Remove all instances longer than max length
        commonMultiples.entrySet().removeIf(entry -> entry.getKey() > knownMaximumKeyLength);

        int totalAmount = commonMultiples.values().stream().mapToInt(Integer::intValue).sum();
        return commonMultiples.entrySet().stream().map(entry -> new KeyLengthProbabilityResult(entry.getKey(), 1.0 * entry.getValue() / totalAmount)).toList();
    }

    private List<KeyLengthProbabilityResult> getAllKeyLengths(int knownMaximumKeyLength) {
        List<KeyLengthProbabilityResult> results = new ArrayList<>();
        for (int i = 1; i <= knownMaximumKeyLength; i++) {
            results.add(new KeyLengthProbabilityResult(i, 1.0 / knownMaximumKeyLength));
        }
        return results;
    }


    private Set<Integer> getDistancesBetweenSubstringReoccurrences(List<SubStringInstance> reoccurringSubstrings) {
        return reoccurringSubstrings.stream()
                .map(SubStringInstance::getDistancesBetweenSubstringInstances)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }


}

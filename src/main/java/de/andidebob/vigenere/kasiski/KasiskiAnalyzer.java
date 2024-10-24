package de.andidebob.vigenere.kasiski;

import de.andidebob.vigenere.kasiski.primefactor.PrimeFactorizer;
import de.andidebob.vigenere.kasiski.substrings.SubStringInstance;
import de.andidebob.vigenere.kasiski.substrings.SubstringReoccurrenceDetector;

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

        int longestSubstring = reoccurringSubstrings.stream().map(i -> i.getString().length()).max(Comparator.comparingInt(Integer::intValue)).get();

        Set<Integer> distancesBetweenReoccurrences = getDistancesBetweenSubstringReoccurrences(reoccurringSubstrings);

        PrimeFactorizer primeFactorizer = new PrimeFactorizer();
        Map<Integer, Integer> commonMultiples = primeFactorizer.getCommonMultiples(distancesBetweenReoccurrences);

        // Remove all instances with less than 5 occurances
        commonMultiples.entrySet().removeIf(entry -> entry.getValue() < 5);
        // Remove all instances with less characters than the longest reoccurrence
        commonMultiples.entrySet().removeIf(entry -> entry.getKey() < longestSubstring || entry.getKey() > knownMaximumKeyLength);

        commonMultiples.forEach((key, value) -> System.out.println(key + " | " + value));

        int totalAmount = commonMultiples.values().stream().mapToInt(Integer::intValue).sum();
        return commonMultiples.entrySet().stream().map(entry -> new KeyLengthProbabilityResult(entry.getKey(), 1.0 * entry.getValue() / totalAmount)).toList();
    }


    private Set<Integer> getDistancesBetweenSubstringReoccurrences(List<SubStringInstance> reoccurringSubstrings) {
        return reoccurringSubstrings.stream()
                .map(SubStringInstance::getDistancesBetweenSubstringInstances)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }


}

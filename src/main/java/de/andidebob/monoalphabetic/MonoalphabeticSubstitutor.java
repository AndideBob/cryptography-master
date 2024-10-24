package de.andidebob.monoalphabetic;

import de.andidebob.frequency.CharacterFrequencyResult;
import de.andidebob.frequency.FrequencyAnalyzer;

public class MonoalphabeticSubstitutor {

    private final FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.lettersOnly();


    public String decypher(String message) {
        CharacterFrequencyResult frequencyResult = frequencyAnalyzer.analyze(message);
        System.out.println(frequencyResult);
        AlphabetMatcher matcher = new AlphabetMatcher(CommonCharacterDefinition.ENGLISH, frequencyResult);
        System.out.println(matcher);
        System.out.println(message);
        return matcher.mapAll(message);
    }
}

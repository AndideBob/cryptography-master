package de.andidebob.textbased.frequency;

import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class FrequencyAnalyzer {

    private final Set<Character> charactersToConsider;

    public FrequencyAnalyzer(String charactersToConsider) {
        this.charactersToConsider = charactersToConsider.toUpperCase()
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toUnmodifiableSet());
    }

    public CharacterFrequencyResult analyze(String input) {
        CharacterFrequencyResult result = new CharacterFrequencyResult(charactersToConsider);
        input.toUpperCase()
                .chars()
                .mapToObj(c -> (char) c)
                .forEach(result::increment);
        return result;
    }

    public static FrequencyAnalyzer lettersOnly() {
        return new FrequencyAnalyzer("abcdefghijklmnopqrstuvwxyz");
    }
}

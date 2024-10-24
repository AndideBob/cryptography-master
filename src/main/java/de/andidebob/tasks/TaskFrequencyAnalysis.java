package de.andidebob.tasks;

import de.andidebob.frequency.CharacterFrequencyResult;
import de.andidebob.frequency.FrequencyAnalyzer;

import java.util.Arrays;

public class TaskFrequencyAnalysis implements TaskHandler {
    @Override
    public void handleInput(String[] lines) {
        FrequencyAnalyzer analyzer = FrequencyAnalyzer.lettersOnly();
        CharacterFrequencyResult result = analyzer.analyze(String.join("", Arrays.asList(lines)));
        System.out.println(result.toString());
    }
}

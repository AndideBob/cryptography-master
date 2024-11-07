package de.andidebob.tasks;

import de.andidebob.frequency.CharacterFrequencyResult;
import de.andidebob.frequency.FrequencyAnalyzer;

import java.util.Arrays;
import java.util.List;

public class TaskFrequencyAnalysis implements TaskHandler {
    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        FrequencyAnalyzer analyzer = FrequencyAnalyzer.lettersOnly();
        CharacterFrequencyResult result = analyzer.analyze(String.join("", Arrays.asList(linesByFile.get(0))));
        System.out.println(result.toString());
        return new String[]{result.toString()};
    }
}

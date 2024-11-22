package de.andidebob.textbased.tasks;

import de.andidebob.textbased.TextBasedTask;
import de.andidebob.textbased.frequency.CharacterFrequencyResult;
import de.andidebob.textbased.frequency.FrequencyAnalyzer;
import de.andidebob.textbased.hexstring.HexString;

import java.util.Arrays;
import java.util.List;

public class TaskFrequencyAnalysis extends TextBasedTask {
    @Override
    public String[] handleInput(List<HexString[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        FrequencyAnalyzer analyzer = FrequencyAnalyzer.lettersOnly();
        CharacterFrequencyResult result = analyzer.analyze(String.join("", Arrays.stream(linesByFile.getFirst()).map(HexString::toString).toList()));
        return result.toString().split("\n");
    }
}

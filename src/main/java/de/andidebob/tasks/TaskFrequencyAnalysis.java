package de.andidebob.tasks;

import de.andidebob.frequency.CharacterFrequencyResult;
import de.andidebob.frequency.FrequencyAnalyzer;
import de.andidebob.otp.hexstring.HexString;

import java.util.Arrays;
import java.util.List;

public class TaskFrequencyAnalysis implements TaskHandler {
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

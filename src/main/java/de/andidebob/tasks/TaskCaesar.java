package de.andidebob.tasks;

import de.andidebob.caesar.CaesarAnalyzer;
import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.otp.hexstring.HexString;

import java.util.ArrayList;
import java.util.List;

public class TaskCaesar implements TaskHandler {
    @Override
    public String[] handleInput(List<HexString[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        CaesarAnalyzer analyzer = new CaesarAnalyzer(new EnglishLanguageModel());
        ArrayList<String> results = new ArrayList<>();
        for (HexString line : linesByFile.get(0)) {
            results.add(analyzer.unshiftCaesar(line.toString()));
        }
        return results.toArray(String[]::new);
    }
}

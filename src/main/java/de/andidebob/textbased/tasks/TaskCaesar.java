package de.andidebob.textbased.tasks;

import de.andidebob.textbased.TextBasedTask;
import de.andidebob.textbased.caesar.CaesarAnalyzer;
import de.andidebob.textbased.hexstring.HexString;
import de.andidebob.textbased.language.EnglishLanguageModel;

import java.util.ArrayList;
import java.util.List;

public class TaskCaesar extends TextBasedTask {
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

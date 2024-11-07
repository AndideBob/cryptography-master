package de.andidebob.tasks;

import de.andidebob.caesar.CaesarAnalyzer;
import de.andidebob.language.EnglishLanguageModel;

import java.util.ArrayList;
import java.util.List;

public class TaskCaesar implements TaskHandler {
    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        CaesarAnalyzer analyzer = new CaesarAnalyzer(new EnglishLanguageModel());
        ArrayList<String> results = new ArrayList<>();
        for (String line : linesByFile.get(0)) {
            System.out.println(analyzer.unshiftCaesar(line));
            results.add(analyzer.unshiftCaesar(line));
        }
        return results.toArray(String[]::new);
    }
}

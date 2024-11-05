package de.andidebob.tasks;

import de.andidebob.caesar.CaesarAnalyzer;
import de.andidebob.language.EnglishLanguageModel;

import java.util.ArrayList;

public class TaskCaesar implements TaskHandler {
    @Override
    public String[] handleInput(String[] lines) {
        CaesarAnalyzer analyzer = new CaesarAnalyzer(new EnglishLanguageModel());
        ArrayList<String> results = new ArrayList<>();
        for (String line : lines) {
            System.out.println(analyzer.unshiftCaesar(line));
            results.add(analyzer.unshiftCaesar(line));
        }
        return results.toArray(String[]::new);
    }
}

package de.andidebob.tasks;

import de.andidebob.caesar.CaesarAnalyzer;
import de.andidebob.language.EnglishLanguageModel;

public class TaskCaesar implements TaskHandler {
    @Override
    public void handleInput(String[] lines) {
        CaesarAnalyzer analyzer = new CaesarAnalyzer(new EnglishLanguageModel());
        for (String line : lines) {
            System.out.println(analyzer.unshiftCaesar(line));
        }
    }
}

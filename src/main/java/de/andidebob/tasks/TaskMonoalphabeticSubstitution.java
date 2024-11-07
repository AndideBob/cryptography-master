package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.monoalphabetic.MonoalphabeticSubstitutor;

import java.util.ArrayList;
import java.util.List;

public class TaskMonoalphabeticSubstitution implements TaskHandler {
    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        //MarkovChainMonteCarloSolver substitutor = new MarkovChainMonteCarloSolver(new EnglishLanguageModel());
        MonoalphabeticSubstitutor substitutor = new MonoalphabeticSubstitutor(new EnglishLanguageModel());
        ArrayList<String> result = new ArrayList<>();
        for (String line : linesByFile.get(0)) {
            String decipheredMessage = substitutor.decryptMessage(line);
            System.out.println(decipheredMessage);
            result.add(decipheredMessage);
        }
        return result.toArray(String[]::new);
    }
}

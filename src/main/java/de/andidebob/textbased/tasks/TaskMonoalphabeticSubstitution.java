package de.andidebob.textbased.tasks;

import de.andidebob.textbased.TextBasedTask;
import de.andidebob.textbased.hexstring.HexString;
import de.andidebob.textbased.language.EnglishLanguageModel;
import de.andidebob.textbased.monoalphabetic.MonoalphabeticSubstitutor;

import java.util.ArrayList;
import java.util.List;

public class TaskMonoalphabeticSubstitution extends TextBasedTask {
    @Override
    public String[] handleInput(List<HexString[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        //MarkovChainMonteCarloSolver substitutor = new MarkovChainMonteCarloSolver(new EnglishLanguageModel());
        MonoalphabeticSubstitutor substitutor = new MonoalphabeticSubstitutor(new EnglishLanguageModel());
        ArrayList<String> result = new ArrayList<>();
        for (HexString line : linesByFile.getFirst()) {
            String decipheredMessage = substitutor.decryptMessage(line.toString());
            System.out.println(decipheredMessage);
            result.add(decipheredMessage);
        }
        return result.toArray(String[]::new);
    }
}

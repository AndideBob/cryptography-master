package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.monoalphabetic.MonoalphabeticSubstitutor;

import java.util.ArrayList;

public class TaskMonoalphabeticSubstitution implements TaskHandler {
    @Override
    public String[] handleInput(String[] lines) {
        //MarkovChainMonteCarloSolver substitutor = new MarkovChainMonteCarloSolver(new EnglishLanguageModel());
        MonoalphabeticSubstitutor substitutor = new MonoalphabeticSubstitutor(new EnglishLanguageModel());
        ArrayList<String> result = new ArrayList<>();
        for (String line : lines) {
            String decipheredMessage = substitutor.decryptMessage(line);
            System.out.println(decipheredMessage);
            result.add(decipheredMessage);
        }
        return result.toArray(String[]::new);
    }
}

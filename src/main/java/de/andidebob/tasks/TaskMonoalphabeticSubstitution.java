package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.monoalphabetic.MonoalphabeticSubstitutor;

public class TaskMonoalphabeticSubstitution implements TaskHandler {
    @Override
    public void handleInput(String[] lines) {
        //MarkovChainMonteCarloSolver substitutor = new MarkovChainMonteCarloSolver(new EnglishLanguageModel());
        MonoalphabeticSubstitutor substitutor = new MonoalphabeticSubstitutor(new EnglishLanguageModel());
        String decypheredMessage = substitutor.decryptMessage(lines[0]);
        System.out.println(decypheredMessage);
    }
}

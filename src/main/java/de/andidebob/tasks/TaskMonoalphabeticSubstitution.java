package de.andidebob.tasks;

import de.andidebob.monoalphabetic.MarkovChainMonteCarloSolver;
import de.andidebob.monoalphabetic.language.EnglishLanguageModel;

public class TaskMonoalphabeticSubstitution implements TaskHandler {
    @Override
    public void handleInput(String[] lines) {
        MarkovChainMonteCarloSolver substitutor = new MarkovChainMonteCarloSolver(new EnglishLanguageModel());
        String decypheredMessage = substitutor.decryptMessage(lines[0]);
        System.out.println(decypheredMessage);
    }
}

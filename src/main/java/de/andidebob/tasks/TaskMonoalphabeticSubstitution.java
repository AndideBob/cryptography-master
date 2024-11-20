package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.monoalphabetic.MonoalphabeticSubstitutor;
import de.andidebob.otp.hexstring.HexString;

import java.util.ArrayList;
import java.util.List;

public class TaskMonoalphabeticSubstitution implements TaskHandler {
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

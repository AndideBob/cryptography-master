package de.andidebob.tasks;

import de.andidebob.monoalphabetic.MonoalphabeticSubstitutor;

public class TaskMonoalphabeticSubstitution implements TaskHandler {
    @Override
    public void handleInput(String[] lines) {
        MonoalphabeticSubstitutor substitutor = new MonoalphabeticSubstitutor();
        String decypheredMessage = substitutor.decypher(lines[0]);
        System.out.println(decypheredMessage);
    }
}

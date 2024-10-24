package de.andidebob.tasks;

public interface TaskHandler {
    void handleInput(String[] lines);

    TaskHandler frequencyAnalysis = new TaskFrequencyAnalysis();
    TaskHandler monoalphabeticSubstitution = new TaskMonoalphabeticSubstitution();
}

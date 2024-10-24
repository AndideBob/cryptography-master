package de.andidebob.tasks;

public interface TaskHandler {
    void handleInput(String[] lines);

    TaskHandler frequencyAnalysis = new TaskFrequencyAnalysis();
    TaskHandler monoalphabeticSubstitution = new TaskMonoalphabeticSubstitution();
    TaskHandler caesar = new TaskCaesar();
    TaskHandler vigenere = new TaskVigenere();
}

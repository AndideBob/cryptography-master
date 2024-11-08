package de.andidebob.tasks;

import java.util.List;

public interface TaskHandler {
    String[] handleInput(List<String[]> linesByFile);

    TaskHandler frequencyAnalysis = new TaskFrequencyAnalysis();
    TaskHandler monoalphabeticSubstitution = new TaskMonoalphabeticSubstitution();
    TaskHandler caesar = new TaskCaesar();
    TaskHandler vigenere = new TaskVigenere();
    TaskHandler otp = new TaskOTP();
    TaskHandler mtp = new TaskMTP();
}

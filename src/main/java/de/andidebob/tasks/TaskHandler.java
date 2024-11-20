package de.andidebob.tasks;

import de.andidebob.otp.hexstring.HexString;

import java.util.List;

public interface TaskHandler {
    String[] handleInput(List<HexString[]> linesByFile);

    TaskHandler task01 = new TaskFrequencyAnalysis();
    TaskHandler task02 = new TaskMonoalphabeticSubstitution();
    TaskHandler task03 = new TaskCaesar();
    TaskHandler task04 = new TaskVigenere();
    TaskHandler task05 = new TaskOTP();
    TaskHandler task06 = new TaskMTP();
    TaskHandler task07 = new TaskAES();
}

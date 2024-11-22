package de.andidebob;

import de.andidebob.databased.tasks.TaskAES;
import de.andidebob.textbased.tasks.*;

public class Tasks {

    public static TaskHandler TASK_01 = new TaskFrequencyAnalysis();
    public static TaskHandler TASK_02 = new TaskMonoalphabeticSubstitution();
    public static TaskHandler TASK_03 = new TaskCaesar();
    public static TaskHandler TASK_04 = new TaskVigenere();
    public static TaskHandler TASK_05 = new TaskOTP();
    public static TaskHandler TASK_06 = new TaskMTP();
    public static TaskHandler TASK_07 = new TaskAES();
}

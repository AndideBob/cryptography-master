package de.andidebob;

import de.andidebob.tasks.TaskHandler;

import static de.andidebob.file.FileUtils.readFile;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Expected arguments for execution, try --help!");
            return;
        } else if (args[0].equals("--help")) {
            printHelp();
            return;
        }
        try {
            String[] lines = readFile(args[0]);
            TaskHandler handler = TaskHandler.vigenere;
            handler.handleInput(lines);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private static void printHelp() {
        System.out.println("Usage: java -jar cryptography-master.jar [input file]");
    }
}
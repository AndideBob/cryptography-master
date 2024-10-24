package de.andidebob;

import de.andidebob.tasks.TaskHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
            TaskHandler handler = TaskHandler.monoalphabeticSubstitution;
            handler.handleInput(lines);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private static String[] readFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[0]);
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar cryptography-master.jar [input file]");
    }
}
package de.andidebob;

import de.andidebob.frequency.CharacterFrequencyResult;
import de.andidebob.frequency.FrequencyAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
            FrequencyAnalyzer analyzer = FrequencyAnalyzer.lettersOnly();
            CharacterFrequencyResult result = analyzer.analyze(String.join("", Arrays.asList(lines)));
            System.out.println(result.toString());
        } catch (IOException e) {
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
package de.andidebob;

import de.andidebob.tasks.TaskHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        String[] outputLines = new String[0];
        try {
            String[] lines = readFile(args[0]);
            TaskHandler handler = TaskHandler.vigenere;
            outputLines = handler.handleInput(lines);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if (args.length >= 2 && outputLines.length > 0) {
            writeOutputToFile(String.join("\n", outputLines), args[1]);
        }

    }

    private static void printHelp() {
        System.out.println("Usage: java -jar cryptography-master.jar [input file]");
    }

    private static void writeOutputToFile(String content, String fileName) {
        try {
            // Get the path of the directory where the JAR is located
            Path jarDir = Paths.get(Main.class
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI())
                    .getParent();

            // Create a file object for the output file in the same directory
            File outputFile = new File(jarDir.toFile(), fileName);

            // Write content to the file
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(content);
                System.out.println("File written to: " + outputFile.getAbsolutePath());
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
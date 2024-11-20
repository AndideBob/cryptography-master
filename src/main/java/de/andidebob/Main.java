package de.andidebob;

import de.andidebob.tasks.TaskHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        FileData fileData = readArguments(args);
        String[] outputLines = new String[0];
        try {
            List<String[]> linesByFile = fileData.inputFiles.stream().map(fileName -> {
                try {
                    return readFile(fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
            TaskHandler handler = TaskHandler.aes;
            outputLines = handler.handleInput(linesByFile);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if (fileData.outputFile != null && outputLines.length > 0) {
            writeOutputToFile(String.join("\n", outputLines), fileData.outputFile);
        }

    }

    private static void printHelp() {
        System.out.println("Usage: java -jar cryptography-master.jar [input file]");
    }

    private static FileData readArguments(String[] args) {
        // Parse and validate arguments
        ArrayList<String> inputFiles = new ArrayList<>();
        String outputFile = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-in":
                    // Ensure -in is followed by at least one filename
                    i++;
                    while (i < args.length && !args[i].startsWith("-")) {
                        inputFiles.add(args[i]);
                        i++;
                    }
                    i--; // Step back one since we may hit a new flag
                    if (inputFiles.isEmpty()) {
                        throw new RuntimeException("Error: -in flag requires at least one input file.");
                    }
                    break;
                case "-out":
                    // Ensure -out is followed by exactly one filename
                    i++;
                    if (i < args.length && !args[i].startsWith("-")) {
                        outputFile = args[i];
                    } else {
                        throw new RuntimeException("Error: -out flag requires exactly one output file.");
                    }
                    break;

                default:
                    // If an unrecognized argument is found, show an error
                    if (!args[i].startsWith("-")) {
                        throw new RuntimeException("Error: Unrecognized argument " + args[i]);
                    }
                    break;
            }
        }
        return new FileData(inputFiles, outputFile);
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

    private record FileData(List<String> inputFiles, String outputFile) {

    }
}
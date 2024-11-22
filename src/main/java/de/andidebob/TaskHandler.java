package de.andidebob;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskHandler {

    public final byte[] handle(String[] args) {
        if (args.length < 1) {
            System.out.println("Expected arguments for execution, try --help!");
            return new byte[0];
        } else if (args[0].equals("--help")) {
            printHelp();
            return new byte[0];
        }
        FileData fileData = readArguments(args);
        return handleWithFileData(fileData);
    }

    protected abstract byte[] handleWithFileData(FileData fileData);

    protected void printHelp() {
        System.out.println("Usage: java -jar cryptography-master.jar -in [input files...] -out [outputFile]");
    }

    protected static FileData readArguments(String[] args) {
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

    protected record FileData(List<String> inputFiles, String outputFile) {

    }
}

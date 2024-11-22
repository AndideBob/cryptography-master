package de.andidebob.textbased;

import de.andidebob.Main;
import de.andidebob.TaskHandler;
import de.andidebob.textbased.hexstring.HexString;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static de.andidebob.file.FileUtils.readFileAsLines;

public abstract class TextBasedTask extends TaskHandler {

    @Override
    protected byte[] handleWithFileData(FileData fileData) {
        String[] outputLines = new String[0];
        try {
            List<HexString[]> linesByFile = fileData.inputFiles().stream().map(fileName -> {
                try {
                    return readFileAsLines(fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
            outputLines = handleInput(linesByFile);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        String result = String.join("\n", outputLines);
        if (fileData.outputFile() != null && outputLines.length > 0) {
            writeOutputToFile(result, fileData.outputFile());
        }
        return result.getBytes();
    }

    protected abstract String[] handleInput(List<HexString[]> linesByFile);

    private void writeOutputToFile(String content, String fileName) {
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

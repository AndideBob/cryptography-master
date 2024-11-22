package de.andidebob.databased;

import de.andidebob.Main;
import de.andidebob.TaskHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static de.andidebob.file.FileUtils.readFileAsBytes;

public abstract class DataBasedTask extends TaskHandler {

    @Override
    protected byte[] handleWithFileData(FileData fileData) {
        FileBytes outputFile = new FileBytes(new byte[0]);
        try {
            List<FileBytes> files = fileData.inputFiles().stream().map(fileName -> {
                try {
                    return new FileBytes(readFileAsBytes(fileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
            outputFile = handleInput(files);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if (fileData.outputFile() != null && outputFile.length() > 0) {
            writeOutputToFile(outputFile, fileData.outputFile());
        }
        return outputFile.getBytes();
    }

    protected abstract FileBytes handleInput(List<FileBytes> files);

    private void writeOutputToFile(FileBytes content, String fileName) {
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

            // Write bytes to the file
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(content.getBytes());
                System.out.println("File written to: " + outputFile.getAbsolutePath());
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}

package de.andidebob.file;

import de.andidebob.textbased.hexstring.HexString;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileUtils {

    public static HexString[] readFileAsLines(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        HexString[] hexStrings = new HexString[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            hexStrings[i] = isHex(lines.get(i)) ? HexString.fromHex(lines.get(i)) : HexString.fromString(lines.get(i));
        }
        return hexStrings;
    }

    private static boolean isHex(String input) {
        if (input == null || input.isEmpty()) {
            return false; // Null or empty strings are not valid hex
        }
        return input.matches("^[0-9a-fA-F]+$");
    }

    public static byte[] readFileAsBytes(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }
}

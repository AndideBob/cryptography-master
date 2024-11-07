package de.andidebob.tasks;

import com.google.common.base.Stopwatch;
import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.vigenere.VigenereDecrypter;
import de.andidebob.vigenere.VignereDecryptionResult;

import java.util.ArrayList;
import java.util.List;


public class TaskVigenere implements TaskHandler {

    private static final int KNOWN_MAX_KEY_LENGTH = 20;

    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        VigenereDecrypter decrypter = new VigenereDecrypter(new EnglishLanguageModel());

        //TODO: Repeating patters in keys
        //TODO: Build docker container
        ArrayList<String> resultLines = new ArrayList<>();

        for (String line : linesByFile.get(0)) {
            System.out.println("Decrypting;");
            System.out.println(line);
            VignereDecryptionResult decryptionResult = decrypter.decrypt(line, KNOWN_MAX_KEY_LENGTH);
            resultLines.add(decryptionResult.output());
            System.out.println(decryptionResult.output());
            System.out.println("Used Key: " + decryptionResult.key());
            System.out.println("LanguageDeviationScore: " + decryptionResult.languageDeviation());
        }
        stopwatch.stop();
        System.out.println("Calculation took: " + stopwatch);
        return resultLines.toArray(String[]::new);
    }
}

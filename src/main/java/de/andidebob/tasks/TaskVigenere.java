package de.andidebob.tasks;

import com.google.common.base.Stopwatch;
import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.vigenere.VigenereDecrypter;
import de.andidebob.vigenere.VignereDecryptionResult;


public class TaskVigenere implements TaskHandler {

    private static final int KNOWN_MAX_KEY_LENGTH = 20;

    @Override
    public void handleInput(String[] lines) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        VigenereDecrypter decrypter = new VigenereDecrypter(new EnglishLanguageModel());

        //TODO: First frequency analysis
        //TODO: Write to Output file
        //TODO: Build docker container

        for (String line : lines) {
            System.out.println("Decrypting;");
            System.out.println(line);
            VignereDecryptionResult decryptionResult = decrypter.decrypt(line, KNOWN_MAX_KEY_LENGTH);
            System.out.println(decryptionResult.output());
            System.out.println("Used Key: " + decryptionResult.key());
            System.out.println("LanguageDeviationScore: " + decryptionResult.languageDeviation());
        }
        stopwatch.stop();
        System.out.println("Calculation took: " + stopwatch);
    }

    private void writeOutput(String output) {

    }
}

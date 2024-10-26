package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.vigenere.VigenereDecrypter;
import de.andidebob.vigenere.VignereDecryptionResult;

public class TaskVigenere implements TaskHandler {

    private static final int KNOWN_MAX_KEY_LENGTH = 20;

    @Override
    public void handleInput(String[] lines) {
        VigenereDecrypter decrypter = new VigenereDecrypter(new EnglishLanguageModel());

        for (String line : lines) {
            System.out.println("Decrypting;");
            System.out.println(line);
            VignereDecryptionResult decryptionResult = decrypter.decrypt(line, KNOWN_MAX_KEY_LENGTH);
            System.out.println(decryptionResult.output());
            System.out.println("Used Key: " + decryptionResult.key());
            System.out.println("LanguageDeviationScore: " + decryptionResult.languageDeviation());
        }
    }
}

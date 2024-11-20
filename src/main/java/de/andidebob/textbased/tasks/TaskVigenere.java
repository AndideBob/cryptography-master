package de.andidebob.textbased.tasks;

import com.google.common.base.Stopwatch;
import de.andidebob.textbased.TextBasedTask;
import de.andidebob.textbased.hexstring.HexString;
import de.andidebob.textbased.language.EnglishLanguageModel;
import de.andidebob.textbased.vigenere.VigenereDecrypter;
import de.andidebob.textbased.vigenere.VignereDecryptionResult;

import java.util.ArrayList;
import java.util.List;


public class TaskVigenere extends TextBasedTask {

    private static final int KNOWN_MAX_KEY_LENGTH = 20;

    @Override
    public String[] handleInput(List<HexString[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        VigenereDecrypter decrypter = new VigenereDecrypter(new EnglishLanguageModel());

        ArrayList<String> resultLines = new ArrayList<>();

        for (HexString line : linesByFile.get(0)) {
            System.out.println("Decrypting;");
            System.out.println(line);
            VignereDecryptionResult decryptionResult = decrypter.decrypt(line.toString(), KNOWN_MAX_KEY_LENGTH);
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

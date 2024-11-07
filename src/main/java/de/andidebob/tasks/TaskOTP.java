package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.otp.HexString;
import de.andidebob.otp.OTPSolver;

import java.util.List;

public class TaskOTP implements TaskHandler {

    private final OTPSolver solver = new OTPSolver(new EnglishLanguageModel());

    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.size() < 2) {
            throw new RuntimeException("Expected 2 input files!");
        }
        String[] ciphertext = linesByFile.get(0).clone();
        String plaintext = linesByFile.get(1)[0];

        HexString[] potentialKeys = determinePotentialKeys(ciphertext, plaintext);

        return solver.decrypt(ciphertext, potentialKeys);
    }

    private HexString[] determinePotentialKeys(String[] ciphertext, String plaintext) {
        HexString[] keys = new HexString[ciphertext.length];
        HexString plainHex = HexString.ofString(plaintext);
        for (int i = 0; i < ciphertext.length; i++) {
            HexString cipherHex = new HexString(ciphertext[i]);
            keys[i] = plainHex.xor(cipherHex);
        }
        return keys;
    }
}

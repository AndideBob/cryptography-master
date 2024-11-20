package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.otp.OTPSolver;
import de.andidebob.otp.hexstring.HexString;

import java.util.List;

public class TaskOTP implements TaskHandler {

    private final OTPSolver solver = new OTPSolver(new EnglishLanguageModel());

    @Override
    public String[] handleInput(List<HexString[]> linesByFile) {
        if (linesByFile.size() < 2) {
            throw new RuntimeException("Expected 2 input files!");
        }
        HexString[] ciphertext = linesByFile.get(0).clone();
        HexString plaintext = linesByFile.get(1)[0];

        HexString[] potentialKeys = determinePotentialKeys(ciphertext, plaintext);

        return solver.decrypt(ciphertext, potentialKeys);
    }

    private HexString[] determinePotentialKeys(HexString[] ciphertext, HexString plaintext) {
        HexString[] keys = new HexString[ciphertext.length];
        for (int i = 0; i < ciphertext.length; i++) {
            keys[i] = plaintext.xor(ciphertext[i]);
        }
        return keys;
    }
}

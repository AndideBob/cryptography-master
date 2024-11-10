package de.andidebob.tasks;

import de.andidebob.otp.MTPSolver;
import de.andidebob.otp.cribdrag.CribDragger;
import de.andidebob.otp.hexstring.BasicHexString;
import de.andidebob.otp.hexstring.XORHexString;

import java.util.List;
import java.util.Set;

public class TaskMTP implements TaskHandler {

    private final MTPSolver solver = new MTPSolver();
    private final CribDragger cribdragger = new CribDragger();

    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        BasicHexString[] cipherLines = prepareHexString(linesByFile.getFirst());
        if (cipherLines.length < 2) {
            throw new RuntimeException("Expected at least 2 lines of input!");
        }

        String ciphertextToDecipher = cipherLines[cipherLines.length - 1].convertToString();

        List<XORHexString> paddedXORs = solver.getPaddedXORs(Set.of(cipherLines));

        //cribdragger.analyze(paddedXORs);
        solver.determineKeyByGuessingProbability(Set.of(cipherLines), ciphertextToDecipher.length());

        return new String[]{""};
    }

    private BasicHexString[] prepareHexString(String[] lines) {
        BasicHexString[] hexStrings = new BasicHexString[lines.length];
        for (int i = 0; i < lines.length; i++) {
            hexStrings[i] = new BasicHexString(lines[i]);
        }
        return hexStrings;
    }
}

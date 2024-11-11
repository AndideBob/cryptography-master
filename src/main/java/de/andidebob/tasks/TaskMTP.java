package de.andidebob.tasks;

import de.andidebob.mtp.MTPSolver;
import de.andidebob.mtp.guessing.MTPKeyGuessSolver;
import de.andidebob.otp.hexstring.BasicHexString;

import java.util.List;
import java.util.Set;

public class TaskMTP implements TaskHandler {

    private final MTPSolver solver = new MTPKeyGuessSolver();

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

        solver.solve(Set.of(cipherLines), ciphertextToDecipher);

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

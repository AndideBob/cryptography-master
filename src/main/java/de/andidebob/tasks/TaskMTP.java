package de.andidebob.tasks;

import de.andidebob.otp.HexString;
import de.andidebob.otp.MTPSolver;

import java.util.List;
import java.util.Set;

public class TaskMTP implements TaskHandler {

    private final MTPSolver solver = new MTPSolver();

    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        HexString[] cipherLines = prepareHexString(linesByFile.getFirst());

        HexString guessedKey = solver.determineKeyByGuessingProbability(Set.of(cipherLines));

        System.out.println("Trying to decrypt:");
        for (HexString cipherLine : cipherLines) {
            System.out.println(cipherLine.xor(guessedKey).convertToString());
        }

        return new String[]{""};
    }

    private HexString[] prepareHexString(String[] lines) {
        HexString[] hexStrings = new HexString[lines.length];
        for (int i = 0; i < lines.length; i++) {
            hexStrings[i] = new HexString(lines[i]);
        }
        return hexStrings;
    }
}

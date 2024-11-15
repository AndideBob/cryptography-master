package de.andidebob.tasks;

import de.andidebob.mtp.MTPSolver;
import de.andidebob.mtp.spaces.MTPSpaceSolver;
import de.andidebob.otp.hexstring.HexString;

import java.util.List;
import java.util.Set;

public class TaskMTP implements TaskHandler {

    private final MTPSolver solver = new MTPSpaceSolver();

    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        HexString[] cipherLines = prepareHexString(linesByFile.getFirst());
        if (cipherLines.length < 2) {
            throw new RuntimeException("Expected at least 2 lines of input!");
        }

        return solver.solve(Set.of(cipherLines));
    }

    private HexString[] prepareHexString(String[] lines) {
        HexString[] hexStrings = new HexString[lines.length];
        for (int i = 0; i < lines.length; i++) {
            hexStrings[i] = HexString.fromHex(lines[i]);
        }
        return hexStrings;
    }
}

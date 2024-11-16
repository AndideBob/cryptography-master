package de.andidebob.tasks;

import de.andidebob.language.EnglishLanguageModel;
import de.andidebob.mtp.spaces.MTPSolver;
import de.andidebob.otp.hexstring.HexString;

import java.util.HashMap;
import java.util.List;

public class TaskMTP implements TaskHandler {

    private final MTPSolver solver = new MTPSolver(new EnglishLanguageModel());

    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        HexString[] cipherLines = prepareHexString(linesByFile.getFirst());
        if (cipherLines.length < 2) {
            throw new RuntimeException("Expected at least 2 lines of input!");
        }

        HexString lineToDecode = cipherLines[cipherLines.length - 1];

        HashMap<HexString, String> solutions = solver.solve(cipherLines);

        solutions.forEach((hex, solution) -> {
            System.out.println("Decoded:");
            System.out.println(hex.toHex());
            System.out.println("To:");
            System.out.println(solution);
        });

        return new String[]{solutions.get(lineToDecode)};
    }

    private HexString[] prepareHexString(String[] lines) {
        HexString[] hexStrings = new HexString[lines.length];
        for (int i = 0; i < lines.length; i++) {
            hexStrings[i] = HexString.fromHex(lines[i]);
        }
        return hexStrings;
    }
}

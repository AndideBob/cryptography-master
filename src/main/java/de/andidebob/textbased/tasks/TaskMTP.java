package de.andidebob.textbased.tasks;

import de.andidebob.textbased.TextBasedTask;
import de.andidebob.textbased.hexstring.HexString;
import de.andidebob.textbased.language.EnglishLanguageModel;
import de.andidebob.textbased.spaces.MTPSolver;

import java.util.HashMap;
import java.util.List;

public class TaskMTP extends TextBasedTask {

    private final MTPSolver solver = new MTPSolver(new EnglishLanguageModel());

    @Override
    public String[] handleInput(List<HexString[]> linesByFile) {
        if (linesByFile.isEmpty()) {
            throw new RuntimeException("Expected input from at least one file");
        }
        HexString[] cipherLines = linesByFile.getFirst();
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
}

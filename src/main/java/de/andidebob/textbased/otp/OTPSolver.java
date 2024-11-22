package de.andidebob.textbased.otp;

import de.andidebob.textbased.frequency.CharacterFrequencyResult;
import de.andidebob.textbased.frequency.FrequencyAnalyzer;
import de.andidebob.textbased.hexstring.HexString;
import de.andidebob.textbased.language.LanguageModel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;

@RequiredArgsConstructor
public class OTPSolver {

    private final LanguageModel languageModel;
    private final FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.lettersOnly();

    public String[] decrypt(HexString[] ciphertextLines, HexString[] potentialKeys) {
        ArrayList<DecryptionResult> results = new ArrayList<>();
        for (HexString potentialKey : potentialKeys) {
            String[] resultLines = new String[ciphertextLines.length];
            for (int i = 0; i < ciphertextLines.length; i++) {
                resultLines[i] = potentialKey.xor(ciphertextLines[i]).toString();
            }
            CharacterFrequencyResult frequencyResult = frequencyAnalyzer.analyze(String.join("", resultLines));
            results.add(new DecryptionResult(resultLines, frequencyResult.getDeviationFromLanguageModel(languageModel)));
        }
        return results.stream().min(Comparator.comparingDouble(DecryptionResult::languageDeviation)).map(DecryptionResult::lines).orElseThrow(() -> new RuntimeException("Expected at least one result!"));
    }

    private record DecryptionResult(String[] lines, double languageDeviation) {

    }
}

package de.andidebob.otp;

import de.andidebob.frequency.CharacterFrequencyResult;
import de.andidebob.frequency.FrequencyAnalyzer;
import de.andidebob.language.LanguageModel;
import de.andidebob.otp.hexstring.HexString;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;

@RequiredArgsConstructor
public class OTPSolver {

    private final LanguageModel languageModel;
    private final FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.lettersOnly();

    public String[] decrypt(String[] ciphertextLines, HexString[] potentialKeys) {
        ArrayList<DecryptionResult> results = new ArrayList<>();
        for (HexString potentialKey : potentialKeys) {
            String[] resultLines = new String[ciphertextLines.length];
            for (int i = 0; i < ciphertextLines.length; i++) {
                HexString cipherHex = HexString.fromHex(ciphertextLines[i]);
                resultLines[i] = potentialKey.xor(cipherHex).toString();
            }
            CharacterFrequencyResult frequencyResult = frequencyAnalyzer.analyze(String.join("", resultLines));
            results.add(new DecryptionResult(resultLines, frequencyResult.getDeviationFromLanguageModel(languageModel)));
        }
        return results.stream().min(Comparator.comparingDouble(DecryptionResult::languageDeviation)).map(DecryptionResult::lines).orElseThrow(() -> new RuntimeException("Expected at least one result!"));
    }

    private record DecryptionResult(String[] lines, double languageDeviation) {

    }
}

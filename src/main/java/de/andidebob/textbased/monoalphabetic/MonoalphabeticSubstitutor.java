package de.andidebob.textbased.monoalphabetic;

import de.andidebob.textbased.alphabet.AlphabetKey;
import de.andidebob.textbased.frequency.CharacterFrequencyResult;
import de.andidebob.textbased.frequency.FrequencyAnalyzer;
import de.andidebob.textbased.language.LanguageModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MonoalphabeticSubstitutor {

    protected final FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.lettersOnly();
    protected final LanguageModel languageModel;

    public String decryptMessage(String message) {
        final CharacterFrequencyResult frequencyResult = frequencyAnalyzer.analyze(message);
        AlphabetKey currentKey = new AlphabetKey(languageModel, frequencyResult);
        System.out.println(currentKey);
        return decrypt(message, currentKey);
    }

    protected String decrypt(String message, AlphabetKey key) {
        return key.mapAll(message);
    }
}

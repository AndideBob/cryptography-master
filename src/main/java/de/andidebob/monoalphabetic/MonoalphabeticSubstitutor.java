package de.andidebob.monoalphabetic;

import de.andidebob.alphabet.AlphabetKey;
import de.andidebob.frequency.CharacterFrequencyResult;
import de.andidebob.frequency.FrequencyAnalyzer;
import de.andidebob.language.LanguageModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MonoalphabeticSubstitutor {

    protected final FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.lettersOnly();
    protected final LanguageModel languageModel;

    public String decryptMessage(String message) {
        final CharacterFrequencyResult frequencyResult = frequencyAnalyzer.analyze(message);
        AlphabetKey currentKey = new AlphabetKey(languageModel, frequencyResult);
        return decrypt(message, currentKey);
    }

    protected String decrypt(String message, AlphabetKey key) {
        return key.mapAll(message);
    }
}

package de.andidebob.textbased.language;

import de.andidebob.textbased.frequency.CharacterFrequency;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class LanguageModel {

    public LanguageModel() {
        if (getFrequencies().size() != 26) {
            throw new RuntimeException("Frequencies must be of length 26");
        }
    }

    public abstract List<BiGram> getBigrams();

    public abstract Map<String, Double> getBigramFrequencyMap();

    public final CharacterFrequency getForCharacters(char c) {
        return getFrequencies().stream().filter(characterFrequency -> characterFrequency.character() == c).findFirst().get();
    }

    public final CharacterFrequency getByRarityIndex(int rarityIndex) {
        List<CharacterFrequency> frequencies = getFrequencies().stream().sorted(CharacterFrequency.comparator).toList();
        if (rarityIndex < 0) {
            return frequencies.getFirst();
        } else if (rarityIndex >= frequencies.size()) {
            return frequencies.getLast();
        }
        return frequencies.get(rarityIndex);
    }

    public abstract Collection<CharacterFrequency> getFrequencies();
}

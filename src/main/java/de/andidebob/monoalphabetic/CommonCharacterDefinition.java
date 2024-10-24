package de.andidebob.monoalphabetic;

public interface CommonCharacterDefinition {
    Character getByRarityIndex(int rarityIndex);

    CommonCharacterDefinition ENGLISH = new EnglishAlphabetCharacterDefinition();
}

package de.andidebob.monoalphabetic;

public class EnglishAlphabetCharacterDefinition implements CommonCharacterDefinition {


    private final Character[] characters = new Character[]{
            'E',
            'T',
            'A',
            'O',
            'N',
            'I',
            'H',
            'S',
            'R',
            'D',
            'L',
            'U',
            'M',
            'C',
            'W',
            'F',
            'Y',
            'G',
            'P',
            'B',
            'V',
            'K',
            'X',
            'J',
            'Q',
            'Z'};

    @Override
    public Character getByRarityIndex(int rarityIndex) {
        if (rarityIndex < 0) {
            return characters[0];
        } else if (rarityIndex >= characters.length) {
            return characters[characters.length - 1];
        }
        return characters[rarityIndex];
    }
}

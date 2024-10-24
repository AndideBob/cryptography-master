package de.andidebob.monoalphabetic.language;

import de.andidebob.frequency.CharacterFrequency;

import java.util.Collection;
import java.util.List;

public class EnglishLanguageModel extends LanguageModel {

    @Override
    public Collection<CharacterFrequency> getFrequencies() {
        return List.of(
                new CharacterFrequency('E', 0, 0.12359f),
                new CharacterFrequency('T', 0, 0.08952f),
                new CharacterFrequency('A', 0, 0.08050f),
                new CharacterFrequency('O', 0, 0.07715f),
                new CharacterFrequency('N', 0, 0.06958f),
                new CharacterFrequency('I', 0, 0.06871f),
                new CharacterFrequency('H', 0, 0.06502f),
                new CharacterFrequency('S', 0, 0.06290f),
                new CharacterFrequency('R', 0, 0.05746f),
                new CharacterFrequency('D', 0, 0.04537f),
                new CharacterFrequency('L', 0, 0.04030f),
                new CharacterFrequency('U', 0, 0.02805f),
                new CharacterFrequency('M', 0, 0.02591f),
                new CharacterFrequency('C', 0, 0.02378f),
                new CharacterFrequency('W', 0, 0.02354f),
                new CharacterFrequency('F', 0, 0.02181f),
                new CharacterFrequency('Y', 0, 0.02119f),
                new CharacterFrequency('G', 0, 0.02042f),
                new CharacterFrequency('P', 0, 0.01682f),
                new CharacterFrequency('B', 0, 0.01494f),
                new CharacterFrequency('V', 0, 0.01032f),
                new CharacterFrequency('K', 0, 0.00853f),
                new CharacterFrequency('X', 0, 0.00145f),
                new CharacterFrequency('J', 0, 0.00127f),
                new CharacterFrequency('Q', 0, 0.00099f),
                new CharacterFrequency('Z', 0, 0.00088f));
    }

    // Bigrams sourced from https://www3.nd.edu/~busiforc/handouts/cryptography/Letter%20Frequencies.html
    @Override
    public List<BiGram> getBigrams() {
        return List.of(
                new BiGram("th", 0.03882543f),
                new BiGram("he", 0.03681391f),
                new BiGram("in", 0.02283899f),
                new BiGram("er", 0.02178042f),
                new BiGram("an", 0.02140460f),
                new BiGram("re", 0.01749394f),
                new BiGram("nd", 0.01571977f),
                new BiGram("on", 0.01418244f),
                new BiGram("en", 0.01383239f),
                new BiGram("at", 0.01335523f),
                new BiGram("ou", 0.01285484f),
                new BiGram("ed", 0.01275779f),
                new BiGram("ha", 0.01274742f),
                new BiGram("to", 0.01169655f),
                new BiGram("or", 0.01151094f),
                new BiGram("it", 0.01134891f),
                new BiGram("is", 0.01109877f),
                new BiGram("hi", 0.01092302f),
                new BiGram("es", 0.01092301f),
                new BiGram("ng", 0.01053385f)
        );
    }

}

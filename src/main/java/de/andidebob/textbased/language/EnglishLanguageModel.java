package de.andidebob.textbased.language;

import de.andidebob.helper.ResourceReader;
import de.andidebob.textbased.frequency.CharacterFrequency;

import java.util.*;

public class EnglishLanguageModel extends LanguageModel {

    private static final String BIGRAM_FILE_NAME = "english_bigram.txt";

    private final List<BiGram> biGrams;
    private final HashMap<String, Double> bigramFrequencyMap;

    public EnglishLanguageModel() {
        try {
            String[] bigramOccurrences = ResourceReader.readResourceAsString(BIGRAM_FILE_NAME);
            biGrams = new ArrayList<>();
            bigramFrequencyMap = new HashMap<>();
            int sumOfOccurrences = Arrays.stream(bigramOccurrences).mapToInt(Integer::parseInt).sum();
            int index = 0;
            for (char first = 'A'; first <= 'Z'; first++) {
                for (char second = 'A'; second <= 'Z'; second++) {
                    String raw = "" + first + second;
                    double probability = 1.0 * Integer.parseInt(bigramOccurrences[index++]) / sumOfOccurrences;
                    biGrams.add(new BiGram(raw, probability));
                    bigramFrequencyMap.put(raw, probability);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load Bigrams for Language Model English!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<BiGram> getBigrams() {
        return Collections.unmodifiableList(biGrams);
    }

    @Override
    public Map<String, Double> getBigramFrequencyMap() {
        return Collections.unmodifiableMap(bigramFrequencyMap);
    }

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
}

package de.andidebob.vigenere;

import de.andidebob.alphabet.AlphabetKey;
import de.andidebob.language.BiGram;
import de.andidebob.language.LanguageModel;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VigenereKeyFinder {

    private final LanguageModel languageModel;

    private final HashMap<String, AlphabetKey[]> keysForBigrams;

    public VigenereKeyFinder(LanguageModel languageModel) {
        this.languageModel = languageModel;
        keysForBigrams = new HashMap<>();
        languageModel.getBigrams()
                .stream()
                .map(BiGram::raw)
                .forEach(bigram -> keysForBigrams.put(bigram, getAlphabetKeyForBigram(bigram)));
    }

    public String findKey(String input, int keyLength) {
        Set<IndexGroup> indexGroups = getIndexGroups(input, keyLength);

        HashMap<IndexGroup, BigramScore> scores = new HashMap<>();
        indexGroups.forEach(group -> scores.put(group, calculateBestBigramScore(group)));
        scores.forEach((group, score) -> {
            System.out.println(group.toString());
            System.out.println("Best Bigram: " + score.toString());
        });
        // TODO Build key
        return "";
    }

    private BigramScore calculateBestBigramScore(IndexGroup group) {
        ArrayList<BigramScore> scores = new ArrayList<>();
        Map<String, Double> bigrams = languageModel.getBigrams()
                .stream()
                .collect(Collectors.toMap(BiGram::raw, BiGram::frequency));
        for (String bigram : bigrams.keySet()) {
            AlphabetKey[] bigramKeys = keysForBigrams.get(bigram);
            double score = 0;
            for (String string : group.strings) {
                char a = bigramKeys[0].map(string.charAt(0));
                char b = bigramKeys[1].map(string.charAt(1));
                String stringToScore = "" + a + b;
                // TODO Review
                score += Math.log(bigrams.get(stringToScore));
            }
            scores.add(new BigramScore(bigram, score));
        }
        return scores.stream()
                .max(Comparator.comparingDouble(BigramScore::score))
                .orElseThrow(() -> new RuntimeException("Could not determine best Bigram!"));
    }

    private Set<IndexGroup> getIndexGroups(String input, int keyLength) {
        HashSet<IndexGroup> indexGroups = new HashSet<>();
        for (int i = 0; i < keyLength; i++) {
            IndexGroup indexGroup = new IndexGroup(i, new HashSet<>());
            for (int pos = 0; pos < input.length(); pos += keyLength) {
                // Iterate in blocks of KeyLength (+1 so the block keyLength to 0 can be considered)
                String block = input.substring(pos, Math.min(pos + keyLength + 1, input.length()));
                // See if block has length for currentGroup
                if (block.length() > i + 1) {
                    // Add Letters of current Group to Group
                    indexGroup.strings.add(block.substring(i, i + 2));
                }
            }
            indexGroups.add(indexGroup);
        }
        return indexGroups;
    }

    private record BigramScore(String bigram, double score) {

    }

    private record IndexGroup(int index, HashSet<String> strings) {
        @Override
        public String toString() {
            return String.format("%d%d -> %s", index, index + 1, strings);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IndexGroup that = (IndexGroup) o;
            return index == that.index;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(index);
        }
    }

    private AlphabetKey[] getAlphabetKeyForBigram(String bigram) {
        int shiftOne = bigram.charAt(0) - 'A';
        int shiftTwo = bigram.charAt(1) - 'A';
        AlphabetKey[] keys = new AlphabetKey[2];
        keys[0] = AlphabetKey.withCaesarShift(shiftOne);
        keys[1] = AlphabetKey.withCaesarShift(shiftTwo);
        return keys;
    }
}

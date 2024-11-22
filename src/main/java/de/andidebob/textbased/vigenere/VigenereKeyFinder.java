package de.andidebob.textbased.vigenere;

import de.andidebob.textbased.alphabet.AlphabetKey;
import de.andidebob.textbased.language.BiGram;
import de.andidebob.textbased.language.LanguageModel;
import lombok.RequiredArgsConstructor;

import java.util.*;

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
        // Need to filter out any non-alphabetic characters and make string uppercase
        String sterilizedInput = sterelizeInput(input);

        Set<IndexGroup> indexGroups = getIndexGroups(sterilizedInput, keyLength);

        HashMap<IndexGroup, BigramScore> scores = new HashMap<>();
        indexGroups.forEach(group -> scores.put(group, calculateBestBigramScore(group)));
        /* Log results
        scores.forEach((group, score) -> {
            System.out.println(group.toString());
            System.out.println("Best Bigram: " + score.toString());
        });
        */
        return buildKey(scores, keyLength);
    }

    private String sterelizeInput(String input) {
        String sterilizedInput = input.replaceAll("[^a-zA-Z]", "");
        return sterilizedInput.toUpperCase();
    }

    private BigramScore calculateBestBigramScore(IndexGroup group) {
        ArrayList<BigramScore> scores = new ArrayList<>();
        Map<String, Double> bigrams = languageModel.getBigramFrequencyMap();
        for (String bigram : bigrams.keySet()) {
            AlphabetKey[] bigramKeys = keysForBigrams.get(bigram);
            double score = 0;
            for (String string : group.strings) {
                char a = bigramKeys[0].map(string.charAt(0));
                char b = bigramKeys[1].map(string.charAt(1));
                String stringToScore = "" + a + b;
                score += Math.log(bigrams.get(stringToScore));
            }
            scores.add(new BigramScore(bigram, score));
        }
        return scores.stream()
                .max(Comparator.comparingDouble(BigramScore::score))
                .orElseThrow(() -> new RuntimeException("Could not determine best Bigram!"));
    }

    private String buildKey(Map<IndexGroup, BigramScore> scoreMap, int keyLength) {
        final Set<IndexGroup> indexGroups = scoreMap.keySet();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            final int currentIndex = i;
            IndexGroup a = indexGroups.stream().filter(group -> group.index == currentIndex).findFirst().orElseThrow(() -> new RuntimeException("Expected Indexgroup with index " + currentIndex + "!"));
            IndexGroup b = indexGroups.stream().filter(group -> group.index == (currentIndex + (keyLength - 1)) % keyLength).findFirst().orElseThrow(() -> new RuntimeException("Expected Indexgroup with index " + ((currentIndex + (keyLength - 1)) % keyLength) + "!"));
            BigramScore scoreA = scoreMap.get(a);
            BigramScore scoreB = scoreMap.get(b);
            if (scoreA.score > scoreB.score) {
                key.append(scoreA.bigram.charAt(0));
            } else {
                key.append(scoreB.bigram.charAt(1));
            }
        }
        return key.toString();
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

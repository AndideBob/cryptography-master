package de.andidebob.vigenere;

import de.andidebob.language.LanguageModel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class VigenereKeyFinder {

    private final LanguageModel languageModel;

    public String findKey(String input, int keyLength) {
        Set<IndexGroup> indexGroups = getIndexGroups(input, keyLength);

        HashMap<IndexGroup, Integer> scores = new HashMap<>();
        indexGroups.forEach(group -> scores.put(group, calculateBestBigramScore(group));
        indexGroups.forEach(System.out::println);
        return "";
    }

    private Integer calculateBestBigramScore(IndexGroup group) {
        // TODO: iterate through all bigrams - caesar shift accordingly - scroe resulting Bigram
        return 1;
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
}

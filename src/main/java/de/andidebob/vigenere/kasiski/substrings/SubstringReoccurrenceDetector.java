package de.andidebob.vigenere.kasiski.substrings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubstringReoccurrenceDetector {

    public List<SubStringInstance> findReoccurringSubstrings(String input, int minSubstringLength, int maxSubstringLength) {
        List<SubStringInstance> reoccurringSubstrings = new ArrayList<>();
        for (int i = maxSubstringLength; i >= minSubstringLength; i--) {
            List<SubStringInstance> newSubstrings = findReoccurringSubstringsOfLength(input, i);
            for (SubStringInstance newSubstring : newSubstrings) {
                if (reoccurringSubstrings.stream()
                        .noneMatch(existingSubstring -> existingSubstring.getString().contains(newSubstring.getString()))) {
                    reoccurringSubstrings.add(newSubstring);
                }
            }
        }
        return reoccurringSubstrings;
    }

    private List<SubStringInstance> findReoccurringSubstringsOfLength(String fullString, int substringLength) {
        HashMap<String, SubStringInstance> substringsWithCount = new HashMap<>();

        for (int i = 0; i <= fullString.length() - substringLength; i++) {
            String substring = fullString.substring(i, i + substringLength);
            if (!substring.matches("[a-zA-Z]+")) {
                continue;
            }

            if (!substringsWithCount.containsKey(substring)) {
                substringsWithCount.put(substring, new SubStringInstance(substring));
            }
            substringsWithCount.get(substring).getIndices().add(i);
        }

        return substringsWithCount.values()
                .stream()
                .filter(subStringInstance -> subStringInstance.getIndices().size() > 1)
                .toList();
    }
}

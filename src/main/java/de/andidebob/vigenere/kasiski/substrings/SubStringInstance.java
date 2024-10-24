package de.andidebob.vigenere.kasiski.substrings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class SubStringInstance {
    private final String string;
    private final Set<Integer> indices = new HashSet<>();

    public Set<Integer> getDistancesBetweenSubstringInstances() {
        // We need to get the distances between all occurrences of the substring
        if (indices.size() < 2) {
            return Collections.emptySet();
        }
        HashSet<Integer> distances = new HashSet<>();
        Integer[] indicesArray = indices.toArray(Integer[]::new);
        for (int i = 0; i < indicesArray.length - 1; i++) {
            for (int j = i + 1; j < indicesArray.length; j++) {
                distances.add(Math.abs(indicesArray[i] - indicesArray[j]));
            }
        }
        return distances;
    }

    @Override
    public String toString() {
        return "SubStringInstance{" +
                "string='" + string + '\'' +
                ", indices=" + indices +
                '}';
    }
}

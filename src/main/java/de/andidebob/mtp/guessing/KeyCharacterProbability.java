package de.andidebob.mtp.guessing;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyCharacterProbability {

    private final HashMap<Character, Integer> map = new HashMap<>();

    public void add(char c) {
        if (!map.containsKey(c)) {
            map.put(c, 1);
        } else {
            map.put(c, map.get(c) + 1);
        }
    }

    public void addAll(Collection<Character> c) {
        for (Character character : c) {
            add(character);
        }
    }

    public List<Character> getCharactersByProbability() {
        return map.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey).toList();
    }
}

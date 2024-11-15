package de.andidebob.mtp.spaces;

import java.util.HashMap;

public class MTPSpaceMap {

    public static final MTPSpaceMap instance = new MTPSpaceMap();
    private static final String charactersToConsider = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789().:,;+- !?";

    private final HashMap<Character, Character> characters = new HashMap<>();

    private MTPSpaceMap() {
        for (char c : charactersToConsider.toCharArray()) {
            char xor = (char) (c ^ ' ');
            if (characters.containsKey(xor)) {
                System.out.println("Duplicate character for value: " + c);
            } else {
                characters.put(xor, c);
            }
        }
    }

    public boolean isCharacterResultOfXOrWithSpace(char c) {
        return characters.containsKey(c);
    }

    public boolean contains(char c) {
        return characters.containsKey(c);
    }

    public char getMappingFor(char c) {
        return characters.get(c);
    }
}

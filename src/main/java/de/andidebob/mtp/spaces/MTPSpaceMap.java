package de.andidebob.mtp.spaces;

import java.util.HashMap;

public class MTPSpaceMap {

    public static final MTPSpaceMap instance = new MTPSpaceMap();
    private static final String charactersToConsider = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.:,;+- \"!?";

    private final HashMap<Character, Character> characters = new HashMap<>();

    private MTPSpaceMap() {
        for (char c : charactersToConsider.toCharArray()) {
            characters.put((char) (c ^ ' '), c);
        }
    }

    public boolean isCharacterResultOfXOrWithSpace(char c) {
        return characters.containsKey(c);
    }
}

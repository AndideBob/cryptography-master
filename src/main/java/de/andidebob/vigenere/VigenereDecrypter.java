package de.andidebob.vigenere;

import de.andidebob.alphabet.AlphabetKey;

public class VigenereDecrypter {

    public String decrypt(String input, String key) {
        AlphabetKey[] keys = new AlphabetKey[key.length()];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = AlphabetKey.withCaesarShift(key.charAt(i) - 'A');
        }
        StringBuilder result = new StringBuilder();
        char[] characters = input.toUpperCase().toCharArray();
        int keyIndex = 0;
        for (char character : characters) {
            if (("" + character).matches("[a-zA-Z]")) {
                result.append(keys[keyIndex].map(character));
                keyIndex = (keyIndex + 1) % keys.length;
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
}

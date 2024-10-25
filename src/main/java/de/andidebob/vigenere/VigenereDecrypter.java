package de.andidebob.vigenere;

import de.andidebob.alphabet.AlphabetKey;

public class VigenereDecrypter {

    public String decrypt(String input, int keyLength) {
        AlphabetKey[] keys = new AlphabetKey[keyLength];
        for (int i = 0; i < keyLength; i++) {
            keys[i] = AlphabetKey.withCaesarShift(i);
        }
        StringBuilder result = new StringBuilder();
        char[] chars = input.toUpperCase().toCharArray();
        int index = 0;
        for (char aChar : chars) {
            if (isValidChar(aChar)) {
                result.append(keys[index % keyLength].map(chars[index]));
                index++;
            } else {
                result.append(aChar);
            }
        }
        return result.toString();
    }

    private boolean isValidChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
}

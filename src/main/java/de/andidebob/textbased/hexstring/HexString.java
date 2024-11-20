package de.andidebob.textbased.hexstring;

import java.util.Arrays;
import java.util.Objects;

public class HexString {

    private final char[] characters;

    public HexString(char[] characters) {
        this.characters = characters;
    }

    public HexString(byte[] bytes) {
        this.characters = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            this.characters[i] = (char) bytes[i];
        }
    }

    public HexString xor(HexString other) {
        int maxLength = Math.max(this.length(), other.length());

        HexString paddedThis = this.padToLength(maxLength);
        HexString paddedOther = other.padToLength(maxLength);

        char[] xorResult = new char[maxLength];
        for (int i = 0; i < maxLength; i++) {
            xorResult[i] = (char) (paddedThis.charAt(i) ^ paddedOther.charAt(i));
        }
        return new HexString(xorResult);
    }

    public HexString padToLength(int length) {
        if (length < characters.length) {
            throw new IllegalArgumentException("Cannot pad HexString that is already longer than length!");
        }
        char[] padded = new char[length];
        int paddingLength = length - characters.length;
        for (int i = 0; i < paddingLength; i++) {
            padded[i] = (char) 0;
        }
        System.arraycopy(characters, 0, padded, paddingLength, characters.length);
        return new HexString(padded);
    }

    public HexString shortenToLength(int length) {
        if (length > characters.length) {
            throw new IllegalArgumentException("Can not shorten HexString that is already shorter than length!");
        }
        char[] shortened = new char[length];
        int start = characters.length - length;
        if (characters.length - start >= 0)
            System.arraycopy(characters, start, shortened, 0, characters.length - start);
        return new HexString(shortened);
    }

    public HexString[] splitToCharacters() {
        HexString[] result = new HexString[characters.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = fromString("" + charAt(i));
        }
        return result;
    }

    public char charAt(int index) {
        return characters[index];
    }

    public int length() {
        return characters.length;
    }

    public String toHex() {
        StringBuilder hexString = new StringBuilder();
        for (char c : characters) {
            hexString.append(String.format("%02X", (int) c));
        }
        return hexString.toString();
    }

    @Override
    public String toString() {
        return new String(characters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HexString hexString = (HexString) o;
        return Objects.deepEquals(characters, hexString.characters);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(characters);
    }

    public static HexString fromHex(String hex) {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have an even length");
        }
        int length = hex.length() / 2;
        char[] characters = new char[length];
        for (int i = 0; i < length; i++) {
            int index = i * 2;
            int value = Integer.parseInt(hex.substring(index, index + 2), 16);
            characters[i] = (char) value;
        }
        return new HexString(characters);
    }

    public static HexString fromString(String str) {
        return new HexString(str.toCharArray());
    }

    public static HexString join(HexString[] hexStrings) {
        StringBuilder joined = new StringBuilder();
        for (HexString hexString : hexStrings) {
            joined.append(hexString.toHex());
        }
        return fromHex(joined.toString());
    }
}

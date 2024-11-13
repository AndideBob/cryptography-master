package de.andidebob.otp.hexstring;

import java.util.Objects;

public class BasicHexString implements HexString {

    private final String hexValue;

    public BasicHexString(String hexValue) {
        this.hexValue = hexValue;
    }

    @Override
    public XORHexString xor(HexString other) {
        int length = Math.max(toString().length(), other.toString().length());
        String hex1 = this.padToLength(length).toString();
        String hex2 = other.padToLength(length).toString();

        // XOR each character and build the result
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit1 = Character.digit(hex1.charAt(i), 16);
            int digit2 = Character.digit(hex2.charAt(i), 16);
            int xorDigit = digit1 ^ digit2;
            result.append(Integer.toHexString(xorDigit));
        }
        return new XORHexString(result.toString(), this, other);
    }

    @Override
    public XORHexString xorWhereRelevant(HexString other) {
        int length = Math.min(toString().length(), other.toString().length());
        String hex1 = this.shortenToLength(length).toString();
        String hex2 = other.shortenToLength(length).toString();

        // XOR each character and build the result
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit1 = Character.digit(hex1.charAt(i), 16);
            int digit2 = Character.digit(hex2.charAt(i), 16);
            int xorDigit = digit1 ^ digit2;
            result.append(Integer.toHexString(xorDigit));
        }
        return new XORHexString(result.toString(), this, other);
    }

    @Override
    public HexString padToLength(int length) {
        String paddedHex = String.format("%" + length + "s", this.hexValue).replace(' ', '0');
        return new BasicHexString(paddedHex);
    }

    @Override
    public HexString shortenToLength(int length) {
        if (hexValue.length() <= length) {
            return new BasicHexString(hexValue);
        }
        return new BasicHexString(hexValue.substring(hexValue.length() - length));
    }

    @Override
    public HexString getHexCharAt(int index) {
        if (index < 0 || index > hexValue.length() - 1) {
            throw new IndexOutOfBoundsException();
        }
        return new BasicHexString(hexValue.substring(index, index + 2));
    }

    @Override
    public char charAt(int index) {
        return hexValue.charAt(index);
    }

    @Override
    public int getLength() {
        return hexValue.length();
    }

    @Override
    public String convertToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexValue.length(); i += 2) {
            String hexPair = hexValue.substring(i, i + 2);
            int decimal = Integer.parseInt(hexPair, 16);
            sb.append((char) decimal);
        }
        return sb.toString();
    }

    @Override
    public String convertToStringForPrint() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexValue.length(); i += 2) {
            String hexPair = hexValue.substring(i, i + 2);
            int decimal = Integer.parseInt(hexPair, 16);
            if (decimal == 0) {
                sb.append('#');
            } else if (decimal == '\n') {
                sb.append('#');
            } else if (decimal == '\r') {
                sb.append('#');
            } else {
                sb.append((char) decimal);
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return hexValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicHexString hexString = (BasicHexString) o;
        return Objects.equals(hexValue, hexString.hexValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hexValue);
    }

    public static BasicHexString ofString(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            String s = Integer.toHexString(c);
            sb.append(s.length() == 1 ? ("0" + s) : s);
        }
        return new BasicHexString(sb.toString());
    }

    public static BasicHexString ofChar(char c) {
        return new BasicHexString(Integer.toHexString(c));
    }

    public static HexString empty(int length) {
        if (length < 1) {
            throw new RuntimeException("Length of empty HexString must be greater than 0");
        }
        return new BasicHexString("").padToLength(length);
    }
}

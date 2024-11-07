package de.andidebob.otp;

import java.util.Objects;

public class HexString {

    private final String hexValue;

    public HexString(String hexValue) {
        this.hexValue = hexValue;
    }

    public HexString xor(HexString other) {
        String hex1 = this.hexValue;
        String hex2 = other.hexValue;
        int length = Math.max(hex1.length(), hex2.length());
        hex1 = String.format("%" + length + "s", hex1).replace(' ', '0');
        hex2 = String.format("%" + length + "s", hex2).replace(' ', '0');

        // XOR each character and build the result
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit1 = Character.digit(hex1.charAt(i), 16);
            int digit2 = Character.digit(hex2.charAt(i), 16);
            int xorDigit = digit1 ^ digit2;
            result.append(Integer.toHexString(xorDigit));
        }
        return new HexString(result.toString());
    }

    public static HexString ofString(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            sb.append(Integer.toHexString(c));
        }
        return new HexString(sb.toString());
    }

    public String convertToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexValue.length(); i += 2) {
            String hexPair = hexValue.substring(i, i + 2);
            int decimal = Integer.parseInt(hexPair, 16);
            if (decimal == 0) {
                sb.append(' ');
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
        HexString hexString = (HexString) o;
        return Objects.equals(hexValue, hexString.hexValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hexValue);
    }
}

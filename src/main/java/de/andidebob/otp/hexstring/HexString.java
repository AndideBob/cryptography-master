package de.andidebob.otp.hexstring;

public interface HexString {

    XORHexString xor(HexString other);

    HexString padToLength(int length);

    HexString getHexCharAt(int index);

    char charAt(int index);

    int getLength();

    String convertToString();
}

package de.andidebob.otp.hexstring;

public interface HexString {

    XORHexString xor(HexString other);

    XORHexString xorWhereRelevant(HexString other);

    HexString padToLength(int length);

    HexString shortenToLength(int length);

    HexString getHexCharAt(int index);

    char charAt(int index);

    int getLength();

    String convertToString();

    String convertToStringForPrint();
}

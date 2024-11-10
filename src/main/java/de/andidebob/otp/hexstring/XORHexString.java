package de.andidebob.otp.hexstring;

import lombok.Getter;

@Getter
public class XORHexString extends BasicHexString {

    private final HexString originalA;
    private final HexString originalB;

    public XORHexString(String hexValue, HexString originalA, HexString originalB) {
        super(hexValue);
        this.originalA = originalA;
        this.originalB = originalB;
    }
}
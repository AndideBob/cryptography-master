package de.andidebob.blockcipher.aes;

import de.andidebob.otp.hexstring.HexString;

public interface AESKeyCalculator {
    HexString[] calculateRoundKeys(HexString key);

    static AESKeyCalculator getKeyCalculatorForKeyLength(int keyLength) {
        if (keyLength == 128) {
            return new AESKeyCalculator128Bit();
        }
        throw new IllegalArgumentException("No Key Calculator defined for KeyLength " + keyLength + "!");
    }
}

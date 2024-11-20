package de.andidebob.databased.blockcipher.aes;

import de.andidebob.databased.blockcipher.ByteBlock;

public interface AESKeyCalculator {
    ByteBlock[] calculateRoundKeys(ByteBlock key);

    static AESKeyCalculator getKeyCalculatorForKeyLength(int keyLength) {
        if (keyLength == 128) {
            return new AESKeyCalculator128Bit();
        }
        throw new IllegalArgumentException("No Key Calculator defined for KeyLength " + keyLength + "!");
    }
}

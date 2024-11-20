package de.andidebob.databased.blockcipher.aes;

import de.andidebob.databased.blockcipher.BlockDecrypter;
import de.andidebob.databased.blockcipher.ByteBlock;

public class AESDecrypter extends BlockDecrypter {

    final ByteBlock[] roundKeys;

    public AESDecrypter(int blockSize, ByteBlock key) {
        super(blockSize, key);
        roundKeys = calculateRoundKeys(key);
        for (int i = 0; i < roundKeys.length; i++) {
            System.out.println("Round key[" + String.format("%02d", i) + "] = " + roundKeys[i].toHex());
        }
    }

    private ByteBlock[] calculateRoundKeys(ByteBlock key) {
        return AESKeyCalculator.getKeyCalculatorForKeyLength(blockSize).calculateRoundKeys(key);
    }

    @Override
    protected ByteBlock decrypt(ByteBlock block) {
        return null;
    }
}

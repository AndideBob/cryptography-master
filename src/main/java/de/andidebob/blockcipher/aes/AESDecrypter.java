package de.andidebob.blockcipher.aes;

import de.andidebob.blockcipher.BlockDecrypter;
import de.andidebob.otp.hexstring.HexString;

public class AESDecrypter extends BlockDecrypter {

    final HexString[] roundKeys;

    public AESDecrypter(int blockSize, HexString key) {
        super(blockSize, key);
        roundKeys = calculateRoundKeys(key);
        for (int i = 0; i < roundKeys.length; i++) {
            System.out.println("Round key[" + String.format("%02d", i) + "] = " + roundKeys[i].toHex());
        }
    }

    private HexString[] calculateRoundKeys(HexString key) {
        return AESKeyCalculator.getKeyCalculatorForKeyLength(blockSize).calculateRoundKeys(key);
    }

    @Override
    protected HexString decrypt(HexString block) {
        return null;
    }
}

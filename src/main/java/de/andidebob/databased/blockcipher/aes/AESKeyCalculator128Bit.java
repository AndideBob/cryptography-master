package de.andidebob.databased.blockcipher.aes;

import de.andidebob.databased.blockcipher.blocks.ByteBlock;

public class AESKeyCalculator128Bit implements AESKeyCalculator {

    private final static ByteBlock[] ROUND_COEFFICIENTS = new ByteBlock[]{
            ByteBlock.fromHex("00"),//Ingnored
            ByteBlock.fromHex("01"),//  1                       =>  00000001
            ByteBlock.fromHex("02"),//  x                       =>  00000010
            ByteBlock.fromHex("04"),//  x^2                     =>  00000100
            ByteBlock.fromHex("08"),//  x^3                     =>  00001000
            ByteBlock.fromHex("10"),//  x^4                     =>  00010000
            ByteBlock.fromHex("20"),//  x^5                     =>  00100000
            ByteBlock.fromHex("40"),//  x^6                     =>  01000000
            ByteBlock.fromHex("80"),//  x^7                     =>  10000000
            ByteBlock.fromHex("1B"),//  x^8 -> x^4+x^3+x+1      =>  00011011
            ByteBlock.fromHex("36"),//  x^9 -> x^5+x^4+x^2+x    =>  00110110
    };

    @Override
    public ByteBlock[] calculateRoundKeys(ByteBlock key) {
        if (key.size() != 16) {
            throw new RuntimeException("Can not calculateKeys for a key value other than 128 bit!");
        }
        ByteBlock[] keys = new ByteBlock[11];
        keys[0] = key;
        for (int i = 1; i < keys.length; i++) {
            keys[i] = getNextKey(keys[i - 1], i);
        }
        return keys;
    }

    private ByteBlock getNextKey(ByteBlock lastKey, int roundNumber) {
        ByteBlock[] split = lastKey.split(4);
        ByteBlock gBoxed = applyGFunction(split[3], roundNumber);
        ByteBlock first = split[0].xor(gBoxed);
        ByteBlock second = split[1].xor(first);
        ByteBlock third = split[2].xor(second);
        ByteBlock fourth = split[3].xor(third);
        return ByteBlock.join(first, second, third, fourth);
    }

    private ByteBlock applyGFunction(ByteBlock block, int roundNumber) {
        ByteBlock shifted = byteShiftLeft(block);
        ByteBlock sBoxed = applySBox(shifted);
        return applyRoundCoefficient(sBoxed, roundNumber);
    }

    private ByteBlock byteShiftLeft(ByteBlock before) {
        return before.shift(-1);
    }

    private ByteBlock applySBox(ByteBlock before) {
        return AESSBox.getInstance().applySBox(before);
    }

    private ByteBlock applyRoundCoefficient(ByteBlock before, int roundNumber) {
        ByteBlock firstByte = new ByteBlock(before.getByte(0));
        ByteBlock xor = firstByte.xor(ROUND_COEFFICIENTS[roundNumber]);
        ByteBlock after = new ByteBlock(before.size());
        after.setByte(0, xor.getByte(0));
        for (int i = 1; i < before.size(); i++) {
            after.setByte(i, before.getByte(i));
        }
        return after;
    }
}

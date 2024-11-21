package de.andidebob.databased.blockcipher.aes;

import de.andidebob.databased.blockcipher.BlockDecrypter;
import de.andidebob.databased.blockcipher.blocks.ByteBlock;
import de.andidebob.databased.blockcipher.blocks.ByteMatrix;

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
        ByteMatrix current = ByteMatrix.of(block);
        for (int round = 0; round < roundKeys.length; round++) {
            final ByteBlock roundKey = roundKeys[roundKeys.length - (round + 1)];
            current = applyKeyAddition(current, roundKey);
            if (round > 0) {
                current = applyInverseMixColumns(current);
            }
            current = applyInverseShiftRows(current);
            current = applyInverseByteSubstitution(current);
        }
        return current.merge();
    }

    private ByteMatrix applyKeyAddition(ByteMatrix matrix, ByteBlock roundKey) {
        ByteBlock[] keyParts = roundKey.split(4);
        ByteBlock[] rows = matrix.getRows();
        ByteMatrix result = new ByteMatrix();
        for (int i = 0; i < rows.length; i++) {
            result.setRow(i, rows[i].xor(keyParts[i]));
        }
        return result;
    }

    private ByteMatrix applyInverseMixColumns(ByteMatrix matrix) {
        // TODO
        return matrix;
    }

    private ByteMatrix applyInverseShiftRows(ByteMatrix matrix) {
        ByteBlock[] rows = matrix.getRows();
        ByteMatrix result = new ByteMatrix();
        for (int i = 0; i < rows.length; i++) {
            result.setRow(i, rows[i].shift(i));
        }
        return result;
    }

    private ByteMatrix applyInverseByteSubstitution(ByteMatrix matrix) {
        // TODO
        return matrix;
    }
}

package de.andidebob.databased.blockcipher.aes;

import de.andidebob.databased.blockcipher.BlockDecrypter;
import de.andidebob.databased.blockcipher.blocks.ByteBlock;
import de.andidebob.databased.blockcipher.blocks.ByteMatrix;

import static de.andidebob.databased.blockcipher.aes.AESDecryptionHelper.applyInverseMixColum;

public class AESDecrypter extends BlockDecrypter {

    final ByteBlock[] roundKeys;

    public AESDecrypter(int blockSize, ByteBlock key) {
        super(blockSize, key);
        roundKeys = calculateRoundKeys(key);
    }

    private ByteBlock[] calculateRoundKeys(ByteBlock key) {
        return AESKeyCalculator.getKeyCalculatorForKeyLength(blockSize).calculateRoundKeys(key);
    }

    @Override
    protected ByteBlock decrypt(ByteBlock block) {
        ByteMatrix current = ByteMatrix.of(block);
        final int lastRound = roundKeys.length - 1;
        for (int round = 0; round <= lastRound; round++) {
            int keyIndex = roundKeys.length - (round + 1);
            final ByteBlock roundKey = roundKeys[keyIndex];
            current = applyKeyAddition(current, roundKey);
            if (round != lastRound) {
                if (round > 0) {
                    current = applyInverseMixColumns(current);
                }
                current = applyInverseShiftRows(current);
                current = applyInverseByteSubstitution(current);
            }
        }
        return current.merge();
    }

    protected ByteMatrix applyKeyAddition(ByteMatrix matrix, ByteBlock roundKey) {
        ByteBlock[] keyParts = roundKey.split(4);
        // Rows or columns?
        ByteBlock[] columns = matrix.getColumns();
        ByteMatrix result = new ByteMatrix();
        for (int i = 0; i < columns.length; i++) {
            result.setColumn(i, columns[i].xor(keyParts[i]));
        }
        return result;
    }

    protected ByteMatrix applyInverseMixColumns(ByteMatrix matrix) {
        ByteMatrix result = new ByteMatrix();
        ByteBlock[] columns = matrix.getColumns();
        for (int i = 0; i < columns.length; i++) {
            result.setColumn(i, applyInverseMixColum(columns[i]));
        }
        return result;
    }

    protected ByteMatrix applyInverseShiftRows(ByteMatrix matrix) {
        ByteBlock[] rows = matrix.getRows();
        ByteMatrix result = new ByteMatrix();
        for (int i = 0; i < rows.length; i++) {
            result.setRow(i, rows[i].shift(i));
        }
        return result;
    }

    protected ByteMatrix applyInverseByteSubstitution(ByteMatrix matrix) {
        ByteMatrix result = new ByteMatrix();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                byte b = matrix.getByte(row, col);
                result.setByte(row, col, AESSBox.getInstance().applyInverseSBox(b).getByte(0));
            }
        }
        return result;
    }
}

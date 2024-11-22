package de.andidebob.databased.blockcipher.aes;

import de.andidebob.databased.blockcipher.blocks.ByteBlock;

public class AESDecryptionHelper {

    public static ByteBlock applyInverseMixColum(ByteBlock column) {
        if (column.size() != 4) {
            throw new IllegalArgumentException("Column size is not 4!");
        }
        ByteBlock result = new ByteBlock(4);
        result.setByte(0, inverseMixRow0(column));
        result.setByte(1, inverseMixRow1(column));
        result.setByte(2, inverseMixRow2(column));
        result.setByte(3, inverseMixRow3(column));
        return result;
    }

    // 0E 0B 0D 09
    private static byte inverseMixRow0(ByteBlock column) {
        ByteBlock a = AESSBox.getInstance().applyMult0E(column.getByte(0));
        ByteBlock b = AESSBox.getInstance().applyMult0B(column.getByte(1));
        ByteBlock c = AESSBox.getInstance().applyMult0D(column.getByte(2));
        ByteBlock d = AESSBox.getInstance().applyMult09(column.getByte(3));
        return a.xor(b).xor(c).xor(d).getByte(0);
    }

    // 09 0E 0B 0D
    private static byte inverseMixRow1(ByteBlock column) {
        ByteBlock a = AESSBox.getInstance().applyMult09(column.getByte(0));
        ByteBlock b = AESSBox.getInstance().applyMult0E(column.getByte(1));
        ByteBlock c = AESSBox.getInstance().applyMult0B(column.getByte(2));
        ByteBlock d = AESSBox.getInstance().applyMult0D(column.getByte(3));
        return a.xor(b).xor(c).xor(d).getByte(0);
    }

    // 0D 09 0E 0B
    private static byte inverseMixRow2(ByteBlock column) {
        ByteBlock a = AESSBox.getInstance().applyMult0D(column.getByte(0));
        ByteBlock b = AESSBox.getInstance().applyMult09(column.getByte(1));
        ByteBlock c = AESSBox.getInstance().applyMult0E(column.getByte(2));
        ByteBlock d = AESSBox.getInstance().applyMult0B(column.getByte(3));
        return a.xor(b).xor(c).xor(d).getByte(0);
    }

    // 0B 0D 09 0E
    private static byte inverseMixRow3(ByteBlock column) {
        ByteBlock a = AESSBox.getInstance().applyMult0B(column.getByte(0));
        ByteBlock b = AESSBox.getInstance().applyMult0D(column.getByte(1));
        ByteBlock c = AESSBox.getInstance().applyMult09(column.getByte(2));
        ByteBlock d = AESSBox.getInstance().applyMult0E(column.getByte(3));
        return a.xor(b).xor(c).xor(d).getByte(0);
    }
}

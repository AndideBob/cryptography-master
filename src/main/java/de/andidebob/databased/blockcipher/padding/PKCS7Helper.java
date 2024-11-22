package de.andidebob.databased.blockcipher.padding;

import de.andidebob.databased.blockcipher.blocks.ByteBlock;

public class PKCS7Helper {

    public static boolean isFullyPadded(ByteBlock in) {
        byte b = getLastByte(in);
        if (b != 16) {
            return false;
        }
        for (int i = 0; i < in.size(); i++) {
            if (in.getByte(i) != b) {
                return false;
            }
        }
        return true;
    }

    public static ByteBlock removePadding(ByteBlock in) {
        byte b = getLastByte(in);
        int lastByteIndex = 16;
        for (int i = 1; i <= b; i++) {
            lastByteIndex = in.size() - i;
            if (in.getByte(lastByteIndex) != b) {
                throw new RuntimeException("Error in padded block!");
            }
        }
        ByteBlock result = new ByteBlock(lastByteIndex);
        for (int i = 0; i < result.size(); i++) {
            result.setByte(i, in.getByte(i));
        }
        return result;
    }

    private static byte getLastByte(ByteBlock in) {
        if (in.size() != 16) {
            throw new IllegalArgumentException("Invalid block size! Expected 16 bytes!");
        }
        byte lastByte = in.getByte(15);
        if (lastByte == 0) {
            throw new IllegalArgumentException("Padding is zero!");
        }
        if (lastByte > 16) {
            throw new IllegalArgumentException("Padding cannot be greater than 16 bytes!");
        }
        return lastByte;
    }
}

package de.andidebob.databased.blockcipher.blocks;

import java.util.Arrays;
import java.util.Objects;

public class ByteBlock {

    private final byte[] data;

    public ByteBlock(byte b) {
        this.data = new byte[]{b};
    }

    public ByteBlock(int size) {
        data = new byte[size];
    }

    public ByteBlock(byte[] data) {
        this.data = data;
    }

    public byte getByte(int index) {
        return data[index];
    }

    public void setByte(int index, byte value) {
        data[index] = value;
    }

    public byte[] getBytes() {
        return Arrays.copyOf(data, data.length);
    }

    public ByteBlock shift(int amount) {
        if (size() == 0) {
            return new ByteBlock(new byte[0]);
        }
        // Normalize
        while (amount < 0) {
            amount += size();
        }
        amount = amount % size();
        // Return same ByteBlock for no shift
        if (amount == 0) {
            return new ByteBlock(getBytes());
        }

        ByteBlock result = new ByteBlock(size());
        for (int i = 0; i < size(); i++) {
            result.setByte((i + amount) % size(), getByte(i));
        }
        return result;
    }

    public int size() {
        return data.length;
    }

    public ByteBlock xor(ByteBlock other) {
        if (other.data.length != data.length) {
            throw new IllegalArgumentException("Byte blocks must have the same length for XOR operation!");
        }

        byte[] xorResult = new byte[data.length];
        for (int i = 0; i < xorResult.length; i++) {
            xorResult[i] = (byte) (data[i] ^ other.data[i]);
        }
        return new ByteBlock(xorResult);
    }

    @Override
    public String toString() {
        return toHex();
    }

    public String toHex() {
        StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteBlock byteBlock = (ByteBlock) o;
        return Objects.deepEquals(data, byteBlock.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    public ByteBlock[] split(int splitCount) {
        if (size() % splitCount != 0) {
            throw new IllegalArgumentException("Size must be a multiple of splitCount!");
        }
        int splitSize = (size() / splitCount);
        ByteBlock[] split = new ByteBlock[splitCount];
        for (int i = 0; i < splitCount; i++) {
            split[i] = new ByteBlock(splitSize);
            for (int j = 0; j < splitSize; j++) {
                split[i].setByte(j, getByte(i * splitSize + j));
            }
        }
        return split;
    }

    public static ByteBlock fromHex(String hex) {
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException("Hex string cannot be null or empty");
        }
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have an even length");
        }

        int byteCount = hex.length() / 2;
        byte[] data = new byte[byteCount];

        for (int i = 0; i < byteCount; i++) {
            int startIndex = i * 2;
            String byteHex = hex.substring(startIndex, startIndex + 2);
            data[i] = (byte) Integer.parseInt(byteHex, 16); // Parse hex pair
        }

        return new ByteBlock(data);
    }

    public static ByteBlock join(ByteBlock[] blocksA, ByteBlock... blocksB) {
        int size = blocksA.length + blocksB.length;
        ByteBlock[] fullArray = new ByteBlock[size];
        int index;
        for (index = 0; index < blocksA.length; index++) {
            fullArray[index] = blocksA[index];
        }
        for (ByteBlock block : blocksB) {
            fullArray[index++] = block;
        }
        return join(fullArray);
    }

    public static ByteBlock join(ByteBlock... blocks) {
        int size = 0;
        for (ByteBlock block : blocks) {
            size += block.size();
        }
        ByteBlock result = new ByteBlock(size);
        int index = 0;
        for (ByteBlock block : blocks) {
            for (int i = 0; i < block.size(); i++) {
                result.setByte(index++, block.getByte(i));
            }
        }
        return result;
    }
}

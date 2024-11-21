package de.andidebob.databased.blockcipher.blocks;

public class ByteMatrix {

    private final ByteBlock[] rows;

    public ByteMatrix() {
        rows = new ByteBlock[4];
        for (int i = 0; i < 4; i++) {
            rows[i] = new ByteBlock(4);
        }
    }

    public ByteBlock[] getRows() {
        return rows.clone();
    }

    public ByteBlock getRow(int i) {
        return rows[i];
    }

    public void setRow(int i, ByteBlock block) {
        if (block.size() != 4) {
            throw new IllegalArgumentException("Block size for Matrix must be 4");
        }
        rows[i] = block;
    }

    public static ByteMatrix of(ByteBlock block) {
        if (block.size() != 16) {
            throw new IllegalArgumentException("Block size for Matrix must be 16");
        }
        ByteMatrix matrix = new ByteMatrix();
        for (int i = 0; i < block.size(); i++) {
            int rowIndex = i % 4;
            int columnIndex = i / 4;
            matrix.rows[rowIndex].setByte(columnIndex, block.getByte(i));
        }
        return matrix;
    }

    public ByteBlock merge() {
        ByteBlock result = new ByteBlock(16);
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < rows.length; row++) {
                result.setByte(col * 4 + row, rows[row].getByte(col));
            }
        }
        return result;
    }
}

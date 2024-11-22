package de.andidebob.databased.blockcipher.blocks;

public class ByteMatrix {

    private final byte[][] data;

    public ByteMatrix() {
        data = new byte[4][4];
    }

    public byte getByte(int row, int col) {
        return data[row][col];
    }

    public void setByte(int row, int col, byte value) {
        data[row][col] = value;
    }

    public ByteBlock[] getRows() {
        ByteBlock[] rows = new ByteBlock[4];
        for (int row = 0; row < 4; row++) {
            rows[row] = new ByteBlock(4);
            for (int col = 0; col < 4; col++) {
                rows[row].setByte(col, getByte(row, col));
            }
        }
        return rows;
    }

    public ByteBlock getRow(int i) {
        return getRows()[i];
    }

    public void setRow(int i, ByteBlock block) {
        if (block.size() != 4) {
            throw new IllegalArgumentException("Block size for Matrix must be 4");
        }
        for (int col = 0; col < 4; col++) {
            setByte(i, col, block.getByte(col));
        }
    }

    public ByteBlock[] getColumns() {
        ByteBlock[] cols = new ByteBlock[4];
        for (int col = 0; col < 4; col++) {
            cols[col] = new ByteBlock(4);
            for (int row = 0; row < 4; row++) {
                cols[col].setByte(row, getByte(row, col));
            }
        }
        return cols;
    }

    public ByteBlock getColumn(int i) {
        return getColumns()[i];
    }

    public void setColumn(int i, ByteBlock block) {
        if (block.size() != 4) {
            throw new IllegalArgumentException("Block size for Matrix must be 4");
        }
        for (int row = 0; row < 4; row++) {
            setByte(row, i, block.getByte(row));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ByteMatrix:");
        for (int row = 0; row < 4; row++) {
            sb.append("\n|");
            for (int col = 0; col < 4; col++) {
                if (col > 0) {
                    sb.append(" ");
                }
                sb.append(String.format("%02X", getByte(row, col)));
            }
            sb.append("|");
        }
        return sb.toString();
    }

    public static ByteMatrix of(ByteBlock block) {
        if (block.size() != 16) {
            throw new IllegalArgumentException("Block size for Matrix must be 16");
        }
        ByteMatrix matrix = new ByteMatrix();
        for (int i = 0; i < block.size(); i++) {
            int rowIndex = i % 4;
            int columnIndex = i / 4;
            matrix.setByte(rowIndex, columnIndex, block.getByte(i));
        }
        return matrix;
    }

    public ByteBlock merge() {
        ByteBlock result = new ByteBlock(16);
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                result.setByte(col * 4 + row, getByte(row, col));
            }
        }
        return result;
    }
}
